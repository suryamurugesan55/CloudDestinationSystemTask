package com.surya.grocerytask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surya.grocerytask.repository.AppRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repository: AppRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}