package com.surya.grocerytask.repository

import com.surya.grocerytask.db.ShoppingListDB
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val shoppingListDB: ShoppingListDB
) {

    suspend fun insertShoppingListWithProducts(
        shoppingList: ShoppingList,
        products: List<ShoppingProducts>
    ) {
        val shoppingListId = shoppingListDB.getShoppingDAO().insertShoppingList(shoppingList)
        products.forEach { it.shoppingListId = shoppingListId }
        shoppingListDB.getShoppingProductDAO().insertShoppingProducts(products)
    }

    suspend fun updateShoppingListAndProducts(
        shoppingList: ShoppingList,
        shoppingProducts: List<ShoppingProducts>
    ) {
        shoppingListDB.getShoppingDAO().updateShoppingList(shoppingList)
        shoppingListDB.getShoppingProductDAO().deleteShoppingProductsByListId(shoppingList.id)
        shoppingProducts.forEach { it.shoppingListId = shoppingList.id }
        shoppingListDB.getShoppingProductDAO().insertShoppingProducts(shoppingProducts)
    }

    suspend fun getListsNotFullyComplete(): List<ShoppingListWithProducts> {
        return shoppingListDB.getShoppingDAO().getListsNotFullyComplete()
    }

    suspend fun getListsFullyCompleteAndHalfPending(): List<ShoppingListWithProducts> {
        return shoppingListDB.getShoppingDAO().getListsFullyCompleteAndHalfPending()
    }

    suspend fun getListsFullyComplete(): List<ShoppingListWithProducts> {
        return shoppingListDB.getShoppingDAO().getListsFullyComplete()
    }

    suspend fun getAllShoppingListsWithProducts(): List<ShoppingListWithProducts> {
        return shoppingListDB.getShoppingDAO().getAllShoppingLists()
    }

    suspend fun scheduleReminderForShoppingList(shoppingListId: Long, reminderTime: Long) {
        shoppingListDB.getShoppingDAO()
            .scheduleReminderForShoppingList(shoppingListId, reminderTime)
    }

}