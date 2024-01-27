package com.surya.grocerytask.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ShoppingList (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val date: Long,
    var reminderTime: Long? = null
   // val products: List<ShoppingProducts>
) : Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = ShoppingList::class,
        parentColumns = ["id"],
        childColumns = ["shoppingListId"],
        onDelete = ForeignKey.CASCADE
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
    var isSuccessful: Boolean = false
) : Serializable
