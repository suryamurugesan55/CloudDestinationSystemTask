package com.surya.grocerytask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingProducts

@Database(entities = [ShoppingList::class, ShoppingProducts::class], version = 1)
abstract class ShoppingListDB : RoomDatabase() {

    abstract fun getShoppingDAO() : ShoppingListDao
    abstract fun getShoppingProductDAO() : ShoppingProductsDao

}