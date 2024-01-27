package com.surya.grocerytask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts
import com.surya.grocerytask.worker.ReminderWorker
import java.util.concurrent.TimeUnit

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingList): Long

    @Transaction
    @Query("SELECT * FROM ShoppingList")
    suspend fun getAllShoppingLists(): List<ShoppingListWithProducts>

    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE id = :shoppingListId")
    fun getShoppingListById(shoppingListId: Long): ShoppingListWithProducts?

    @Update
    suspend fun updateShoppingList(shoppingList: ShoppingList)

    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE id NOT IN (SELECT DISTINCT shoppingListId FROM ShoppingProducts WHERE isSuccessful = 1)")
    suspend fun getListsNotFullyComplete(): List<ShoppingListWithProducts>

    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE id IN (SELECT DISTINCT shoppingListId FROM ShoppingProducts WHERE isSuccessful = 1) AND id IN (SELECT DISTINCT shoppingListId FROM ShoppingProducts WHERE isSuccessful = 0)")
    suspend fun getListsFullyCompleteAndHalfPending(): List<ShoppingListWithProducts>

    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE id IN (SELECT DISTINCT shoppingListId FROM ShoppingProducts WHERE isSuccessful = 1) AND id NOT IN (SELECT DISTINCT shoppingListId FROM ShoppingProducts WHERE isSuccessful = 0)")
    suspend fun getListsFullyComplete(): List<ShoppingListWithProducts>


    // reminder

    @Transaction
    @Query("SELECT * FROM ShoppingList WHERE id = :shoppingListId")
    suspend fun getShoppingListWithProducts(shoppingListId: Long): ShoppingListWithProducts?

    suspend fun scheduleReminderForShoppingList(shoppingListId: Long, reminderTime: Long) {
        val shoppingList = getShoppingListWithProducts(shoppingListId)
        shoppingList?.let {
            it.shoppingList.reminderTime = reminderTime
            updateShoppingList(it.shoppingList)
            scheduleReminderWork(shoppingListId, reminderTime)
        }
    }


    private fun scheduleReminderWork(shoppingListId: Long, reminderTime: Long) {
        val workManager = WorkManager.getInstance()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val inputData = Data.Builder()
            .putLong("shoppingListId", shoppingListId)
            .build()

        val reminderRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(inputData)
            .setInitialDelay(reminderTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            "reminder_$shoppingListId",
            ExistingWorkPolicy.REPLACE,
            reminderRequest
        )
    }
}

@Dao
interface ShoppingProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingProducts(products: List<ShoppingProducts>)

    @Query("DELETE FROM ShoppingProducts WHERE shoppingListId = :shoppingListId")
    suspend fun deleteShoppingProductsByListId(shoppingListId: Long): Int

    @Query("UPDATE ShoppingProducts SET isSuccessful = :newStatus WHERE id = :productId")
    suspend fun updateIsSuccessful(productId: Long, newStatus: Boolean)
}
