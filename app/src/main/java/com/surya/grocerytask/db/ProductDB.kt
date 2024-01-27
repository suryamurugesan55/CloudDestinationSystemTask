package com.surya.grocerytask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surya.grocerytask.model.ProductList

@Database(entities = [ProductList::class], version = 1)
abstract class ProductDB : RoomDatabase() {

    abstract fun getroomDAO(): ProductDAO

}