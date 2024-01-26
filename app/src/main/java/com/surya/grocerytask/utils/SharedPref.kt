package com.surya.grocerytask.utils

import android.content.Context
import com.surya.grocerytask.R

class SharedPref(context: Context) {
    private val pref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    var isFirstTime: Boolean
        get() = pref.contains(KEY_IS_FIRST) && pref.getBoolean(KEY_IS_FIRST, false)
        set(isFirstTime) = pref.edit().putBoolean(KEY_IS_FIRST, isFirstTime).apply()

    companion object {
        private const val KEY_IS_FIRST = "Is_First"
    }
}