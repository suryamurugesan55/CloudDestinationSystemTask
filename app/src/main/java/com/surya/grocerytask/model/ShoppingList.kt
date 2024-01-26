package com.surya.grocerytask.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class ShoppingList (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val date: Long,
   // val products: List<ShoppingProducts>
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = ShoppingList::class,
        parentColumns = ["id"],
        childColumns = ["shoppingListId"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class ShoppingProducts (
    var shoppingListId: Long,
    val category: String,
    val description: String,
    val id: Int = 0,
    @PrimaryKey(autoGenerate = true)
    val keyId: Int = 0,
    val image: String,
    val price: Double,
    val title: String,
    val isSuccessful: Boolean = false
)
