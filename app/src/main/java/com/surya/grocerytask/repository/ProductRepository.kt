package com.surya.grocerytask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.retrofit.ApiService
import com.surya.grocerytask.utils.SharedPref
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService, private val sharedPref: SharedPref){
    private val _products = MutableLiveData<List<ProductList>>()
    val groceryItem: LiveData<List<ProductList>> get() = _products

    suspend fun getGroceryItems() {
        val result = apiService.getProducts()
        if(result.isSuccessful && result.body() != null){
            sharedPref.isFirstTime = true
            _products.postValue(result.body())
        }
    }
}