package com.surya.grocerytask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.repository.AppRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository): ViewModel() {
    val groceryLiveData : LiveData<List<ProductList>> get() = repository.groceryItem

    init {
        viewModelScope.launch {
            repository.getGroceryItems()
        }
    }
}