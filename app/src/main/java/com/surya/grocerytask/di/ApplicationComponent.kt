package com.surya.grocerytask.di

import android.content.Context
import com.surya.grocerytask.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import java.security.AccessControlContext
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, PrefModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }

}