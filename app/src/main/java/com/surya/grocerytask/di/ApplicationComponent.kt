package com.surya.grocerytask.di

import android.content.Context
import com.surya.grocerytask.ui.MainActivity
import com.surya.grocerytask.ui.SplashActivity
import com.surya.grocerytask.ui.todo.TodoScreenFragment
import com.surya.grocerytask.ui.todo.add_shopping.AddShoppingActivity
import dagger.BindsInstance
import dagger.Component
import java.security.AccessControlContext
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, PrefModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(addShoppingActivity: AddShoppingActivity)
    fun inject(todoScreenFragment: TodoScreenFragment)
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }

}