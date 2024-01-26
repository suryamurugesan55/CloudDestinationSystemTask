package com.surya.grocerytask.di

import android.content.Context
import android.content.SharedPreferences
import com.surya.grocerytask.R
import com.surya.grocerytask.utils.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefModule {
    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPref {
        return SharedPref(context)
    }
}