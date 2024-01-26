package com.surya.grocerytask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.surya.grocerytask.model.ProductList

@Dao
interface ProductDAO {

    @Insert
    suspend fun addProducts(productList: List<ProductList>)

    @Query("SELECT * FROM ProductList")
    suspend fun getProducts() : List<ProductList>
}