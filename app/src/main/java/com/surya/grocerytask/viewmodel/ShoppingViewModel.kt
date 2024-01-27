package com.surya.grocerytask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts
import com.surya.grocerytask.repository.ShoppingRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(private val repository: ShoppingRepository) :
    ViewModel() {

    private val _shoppingListsWithProducts = MutableLiveData<List<ShoppingListWithProducts>>()
    val shoppingListsWithProducts: LiveData<List<ShoppingListWithProducts>> get() = _shoppingListsWithProducts

    init {
        viewModelScope.launch {
            _shoppingListsWithProducts.value = repository.getAllShoppingListsWithProducts()
        }
    }

    fun getListsNotFullyComplete(): LiveData<List<ShoppingListWithProducts>> {
        return liveData {
            val lists = repository.getListsNotFullyComplete()
            emit(lists)
        }
    }

    fun getListsFullyCompleteAndHalfPending(): LiveData<List<ShoppingListWithProducts>> {
        return liveData {
            val lists = repository.getListsFullyCompleteAndHalfPending()
            emit(lists)
        }
    }

    fun getListsFullyComplete(): LiveData<List<ShoppingListWithProducts>> {
        return liveData {
            val lists = repository.getListsFullyComplete()
            emit(lists)
        }
    }

    fun insertShoppingListWithProducts(
        shoppingList: ShoppingList,
        products: List<ShoppingProducts>
    ) {
        viewModelScope.launch {
            repository.insertShoppingListWithProducts(shoppingList, products)
            _shoppingListsWithProducts.value = repository.getAllShoppingListsWithProducts()
        }
    }

    fun updateShoppingListWithProducts(
        shoppingList: ShoppingList,
        products: List<ShoppingProducts>
    ) {
        viewModelScope.launch {
            repository.updateShoppingListAndProducts(shoppingList, products)
            _shoppingListsWithProducts.value = repository.getAllShoppingListsWithProducts()
        }
    }

    fun setReminderForShoppingList(
        shoppingListId: Long,
        reminderTime: Long,
    ) {
        viewModelScope.launch {
            repository.scheduleReminderForShoppingList(shoppingListId, reminderTime)
        }
    }
}