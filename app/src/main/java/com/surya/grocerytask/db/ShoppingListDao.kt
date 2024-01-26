package com.surya.grocerytask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingList): Long

    @Transaction
    @Query("SELECT * FROM ShoppingList")
    suspend fun getAllShoppingLists(): List<ShoppingListWithProducts>
}

@Dao
interface ShoppingProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingProducts(products: List<ShoppingProducts>)

    @Query("SELECT * FROM ShoppingProducts WHERE shoppingListId = :shoppingListId")
    suspend fun getProductsForShoppingList(shoppingListId: Long): List<ShoppingProducts>
}
