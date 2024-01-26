package com.surya.grocerytask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository): ViewModel() {
    val groceryLiveData : LiveData<List<ProductList>> get() = repository.groceryItem

    init {
        viewModelScope.launch {
            repository.getGroceryItems()
        }
    }
}