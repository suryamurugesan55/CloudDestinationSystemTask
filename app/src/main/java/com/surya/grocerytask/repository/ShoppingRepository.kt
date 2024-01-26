package com.surya.grocerytask.repository

import com.surya.grocerytask.db.ShoppingListDB
import com.surya.grocerytask.db.ShoppingListDao
import com.surya.grocerytask.db.ShoppingProductsDao
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val shoppingListDB: ShoppingListDB
){

    suspend fun insertShoppingListWithProducts(shoppingList: ShoppingList, products: List<ShoppingProducts>) {
        val shoppingListId = shoppingListDB.getShoppingDAO().insertShoppingList(shoppingList)
        products.forEach { it.shoppingListId = shoppingListId }
        shoppingListDB.getShoppingProductDAO().insertShoppingProducts(products)
    }

    suspend fun getAllShoppingListsWithProducts(): List<ShoppingListWithProducts> {
        return shoppingListDB.getShoppingDAO().getAllShoppingLists()
    }

    suspend fun getProductsForShoppingList(shoppingListId: Long): List<ShoppingProducts> {
        return shoppingListDB.getShoppingProductDAO().getProductsForShoppingList(shoppingListId)
    }

}