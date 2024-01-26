package com.surya.grocerytask.retrofit

import com.surya.grocerytask.model.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductList>>
}