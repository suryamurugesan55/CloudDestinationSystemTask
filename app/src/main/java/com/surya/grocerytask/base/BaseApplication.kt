package com.surya.grocerytask.base

import android.app.Application
import com.surya.grocerytask.di.ApplicationComponent
import com.surya.grocerytask.di.DaggerApplicationComponent

class BaseApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}