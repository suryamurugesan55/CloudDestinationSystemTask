package com.surya.grocerytask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.repository.ProductItemRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductItemViewModel @Inject constructor(private val productItemRepository: ProductItemRepository) : ViewModel(){

    private val _components = MutableLiveData<List<ProductList>>()
    val components: LiveData<List<ProductList>> get() = _components

    init {
        viewModelScope.launch {
            _components.value = productItemRepository.getAllProducts()
        }
    }

}