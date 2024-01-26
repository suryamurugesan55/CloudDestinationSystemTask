package com.surya.grocerytask.model

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingListWithProducts(
    @Embedded
    val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val products: List<ShoppingProducts>
)
