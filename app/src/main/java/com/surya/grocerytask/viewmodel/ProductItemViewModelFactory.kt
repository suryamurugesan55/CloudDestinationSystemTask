package com.surya.grocerytask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surya.grocerytask.repository.ProductItemRepository
import javax.inject.Inject

class ProductItemViewModelFactory @Inject constructor(private val productItemRepository: ProductItemRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductItemViewModel(productItemRepository) as T
    }

}