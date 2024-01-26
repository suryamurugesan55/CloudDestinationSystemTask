package com.surya.grocerytask.di

import android.content.Context
import androidx.room.Room
import com.surya.grocerytask.db.roomDB
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
    fun provideRoomDB(context: Context) : roomDB{
        return Room.databaseBuilder(context, roomDB::class.java, "roomDB").build()
    }
}