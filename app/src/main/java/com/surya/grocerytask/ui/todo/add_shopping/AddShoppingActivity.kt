package com.surya.grocerytask.ui.todo.add_shopping

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.CollectionUtils
import com.surya.grocerytask.R
import com.surya.grocerytask.adapter.ProductItemAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.ActivityAddShoppingBinding
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingProducts
import com.surya.grocerytask.viewmodel.ProductItemViewModel
import com.surya.grocerytask.viewmodel.ProductItemViewModelFactory
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import javax.inject.Inject


class AddShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddShoppingBinding

    private lateinit var shoppingViewModel: ShoppingViewModel
    @Inject
    lateinit var shoppingViewModelFactory: ShoppingViewModelFactory

    private lateinit var productItemViewModel: ProductItemViewModel
    @Inject
    lateinit var productItemViewModelFactory: ProductItemViewModelFactory

    private lateinit var productItemAdapter: ProductItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_shopping)
        binding = ActivityAddShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as BaseApplication).applicationComponent.inject(this)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Shopping List"
            setDisplayHomeAsUpEnabled(true)
            val color = ContextCompat.getColor(this@AddShoppingActivity, R.color.white)
            val drawable = ContextCompat.getDrawable(this@AddShoppingActivity, R.drawable.ic_arrow_back)
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                setHomeAsUpIndicator(drawable)
            }
            binding.toolbar.setTitleTextColor(Color.WHITE)
        }

        productItemViewModel = ViewModelProvider(this, productItemViewModelFactory)[ProductItemViewModel::class.java]

        productItemAdapter = ProductItemAdapter(this@AddShoppingActivity)

        productItemViewModel.components.observe(this, Observer {
            productItemAdapter.setComponents(it)
        })

        binding.recyclerView.adapter = productItemAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        shoppingViewModel = ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]

//        viewModel.shoppingListsWithProducts.observe(this, Observer { shoppingListsWithProducts ->
//            // Update UI with shopping lists and associated products
//
//            Log.e("*****",""+shoppingListsWithProducts.joinToString { x ->
//                x.products.toString() +" ((())) "+ x.shoppingList.toString()
//            })
//
//        })

        binding.btnSubmit.setOnClickListener {
            val isEmpty = CollectionUtils.isEmpty(ProductItemAdapter.selectedList)
            if(isEmpty) {

            } else {
                val list = ProductItemAdapter.selectedList!!
                val selectedList: ArrayList<ShoppingProducts> = arrayListOf()
                for (item in list) {
                    if (item.isChecked) {
                        selectedList.add(
                            ShoppingProducts(
                            id = item.id,
                            isSuccessful = false,
                            description = item.description,
                            category = item.category,
                            image = item.image,
                            price = item.price,
                            shoppingListId = 0,
                            title = item.title
                        )
                        )
                    }
                }
                val shopp: List<ShoppingProducts> = selectedList
                val newShoppingList = ShoppingList(name = binding.etName.text.toString().trim(), date = System.currentTimeMillis())
                shoppingViewModel.insertShoppingListWithProducts(newShoppingList, shopp)
            }
        }

        // Example: Insert a new shopping list with products
       /* val newShoppingList = ShoppingList(name = "Groceries", date = System.currentTimeMillis())
        val newProduct1 = ShoppingProducts(
            shoppingListId = 0, // It will be updated during insertion
            //id = 29,
            category = "Food",
            description = "Bread",
            image = "",
            price = 2.0,
            title = "Whole Wheat Bread",
            isSuccessful = false
        )
        val newProduct2 = ShoppingProducts(
            shoppingListId = 0, // It will be updated during insertion
            //id = 30,
            category = "Beverages",
            description = "Coffee",
            image = "",
            price = 5.0,
            title = "Arabica Coffee",
            isSuccessful = false
        )

        shoppingViewModel.insertShoppingListWithProducts(newShoppingList, listOf(newProduct1, newProduct2))*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this@AddShoppingActivity)
            .setMessage("Do you want to exit?")
            .setTitle("Alert!")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }
}