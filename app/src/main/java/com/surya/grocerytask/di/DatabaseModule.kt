package com.surya.grocerytask.di

import android.content.Context
import androidx.room.Room
import com.surya.grocerytask.db.ProductDB
import com.surya.grocerytask.db.ShoppingListDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDB(context: Context) : ProductDB{
        return Room.databaseBuilder(context, ProductDB::class.java, "roomDB").build()
    }

    @Singleton
    @Provides
    fun provideShoppingDB(context: Context) : ShoppingListDB{
        return Room.databaseBuilder(context, ShoppingListDB::class.java, "shoppingDB").build()
    }
}