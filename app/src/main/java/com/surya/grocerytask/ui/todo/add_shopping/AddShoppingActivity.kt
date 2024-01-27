package com.surya.grocerytask.ui.todo.add_shopping

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
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
import com.surya.grocerytask.ui.MainActivity
import com.surya.grocerytask.viewmodel.ProductItemViewModel
import com.surya.grocerytask.viewmodel.ProductItemViewModelFactory
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import java.util.UUID
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
            title = "Add Shopping List"
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

        binding.btnSubmit.setOnClickListener {
            val isEmpty = CollectionUtils.isEmpty(ProductItemAdapter.selectedList)
            if(binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this@AddShoppingActivity, "Please enter TODO Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(isEmpty) {
                Toast.makeText(this@AddShoppingActivity, "Min Select 1 product", Toast.LENGTH_SHORT).show()
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
                //Log.e("66666",""+ list.toString())
                val shopp: List<ShoppingProducts> = selectedList
                val newShoppingList = ShoppingList(name = binding.etName.text.toString().trim(), date = System.currentTimeMillis())
                shoppingViewModel.insertShoppingListWithProducts(newShoppingList, shopp)
                moveHome()
            }
        }

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