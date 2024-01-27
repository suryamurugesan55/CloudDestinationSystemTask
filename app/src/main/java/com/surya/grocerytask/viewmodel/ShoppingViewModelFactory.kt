package com.surya.grocerytask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surya.grocerytask.repository.ShoppingRepository
import javax.inject.Inject

class ShoppingViewModelFactory @Inject constructor(private val repository: ShoppingRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(repository) as T
    }
}