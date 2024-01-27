package com.surya.grocerytask.ui.todo.add_shopping

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.CollectionUtils
import com.surya.grocerytask.R
import com.surya.grocerytask.adapter.ProductItemAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.ActivityAddShoppingBinding
import com.surya.grocerytask.databinding.ActivityEditShoppingBinding
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts
import com.surya.grocerytask.ui.MainActivity
import com.surya.grocerytask.viewmodel.ProductItemViewModel
import com.surya.grocerytask.viewmodel.ProductItemViewModelFactory
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import java.util.UUID
import javax.inject.Inject

class EditShoppingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditShoppingBinding

    private lateinit var shoppingViewModel: ShoppingViewModel
    @Inject
    lateinit var shoppingViewModelFactory: ShoppingViewModelFactory

    private lateinit var productItemViewModel: ProductItemViewModel
    @Inject
    lateinit var productItemViewModelFactory: ProductItemViewModelFactory

    private lateinit var productItemAdapter: ProductItemAdapter

    // get from intent
    lateinit var shoppingListWithProducts: ShoppingListWithProducts

    lateinit var shoppingProducts: List<ShoppingProducts>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit_shopping)
        binding = ActivityEditShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as BaseApplication).applicationComponent.inject(this)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Edit Shopping List"
            setDisplayHomeAsUpEnabled(true)
            val color = ContextCompat.getColor(this@EditShoppingActivity, R.color.white)
            val drawable = ContextCompat.getDrawable(this@EditShoppingActivity, R.drawable.ic_arrow_back)
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                setHomeAsUpIndicator(drawable)
            }
            binding.toolbar.setTitleTextColor(Color.WHITE)
        }

        getDataFromIntent()

        productItemViewModel = ViewModelProvider(this, productItemViewModelFactory)[ProductItemViewModel::class.java]

        productItemAdapter = ProductItemAdapter(this@EditShoppingActivity)

        productItemViewModel.components.observe(this, Observer {
            var list = it
            Log.e("99999",""+shoppingProducts.toString())
            shoppingProducts.forEach { shopProduct ->
                list.find { it.id == shopProduct.id }?.isChecked = true
            }

            Log.e("99999",""+it.toString())
            productItemAdapter.setComponents(list)
        })

        binding.recyclerView.adapter = productItemAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        shoppingViewModel = ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]

        binding.btnSubmit.setOnClickListener {
            val isEmpty = CollectionUtils.isEmpty(ProductItemAdapter.selectedList)
            if(binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this@EditShoppingActivity, "Please enter TODO Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(isEmpty) {
                Toast.makeText(this@EditShoppingActivity, "Min Select 1 product", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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
                val existingShoppingListId = shoppingListWithProducts.shoppingList.id
                val newShoppingList = ShoppingList(id = existingShoppingListId, name = binding.etName.text.toString().trim(), date = shoppingListWithProducts.shoppingList.date, shoppingListWithProducts.shoppingList.reminderTime)
                shoppingViewModel.updateShoppingListWithProducts(newShoppingList, shopp)
                moveHome()
            }

        }

    }

    private fun getDataFromIntent() {
        val receivedIntent = intent
        if (receivedIntent != null && receivedIntent.hasExtra("edit_data")) {
            val edit_data = receivedIntent.getSerializableExtra("edit_data")
            if (edit_data != null) {
                shoppingListWithProducts = edit_data as ShoppingListWithProducts
            }
        }

        setData()
    }

    private fun setData() {
        binding.etName.setText(shoppingListWithProducts.shoppingList.name)
        shoppingProducts = shoppingListWithProducts.products
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
        AlertDialog.Builder(this@EditShoppingActivity)
            .setMessage("Do you want to exit?")
            .setTitle("Alert!")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                moveHome()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun moveHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}