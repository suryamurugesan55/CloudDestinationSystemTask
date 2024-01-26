package com.surya.grocerytask.repository

import com.surya.grocerytask.db.ProductDAO
import com.surya.grocerytask.db.ProductDB
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.retrofit.ApiService
import com.surya.grocerytask.utils.SharedPref
import javax.inject.Inject

class ProductItemRepository @Inject constructor(private val productDB: ProductDB) {
    suspend fun getAllProducts(): List<ProductList> {
        return productDB.getroomDAO().getProducts()
    }
}