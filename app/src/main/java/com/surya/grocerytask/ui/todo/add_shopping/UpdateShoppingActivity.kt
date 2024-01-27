package com.surya.grocerytask.ui.todo.add_shopping

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.CollectionUtils
import com.surya.grocerytask.R
import com.surya.grocerytask.adapter.ProductItemAdapter
import com.surya.grocerytask.adapter.ShoppingStatusUpdateAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.ActivityUpdateShoppingBinding
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.model.ShoppingProducts
import com.surya.grocerytask.ui.MainActivity
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import javax.inject.Inject

class UpdateShoppingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateShoppingBinding

    private lateinit var shoppingViewModel: ShoppingViewModel
    @Inject
    lateinit var shoppingViewModelFactory: ShoppingViewModelFactory

    // get from intent
    private lateinit var shoppingListWithProducts: ShoppingListWithProducts
    private lateinit var shoppingProducts: List<ShoppingProducts>
    private lateinit var from: String

    // adapter
    private lateinit var shoppingStatusUpdateAdapter: ShoppingStatusUpdateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_update_shopping)

        binding = ActivityUpdateShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as BaseApplication).applicationComponent.inject(this)

        getDataFromIntent()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = if(from == "complete") "Complete Shopping List" else "Update Shopping List"
            setDisplayHomeAsUpEnabled(true)
            val color = ContextCompat.getColor(this@UpdateShoppingActivity, R.color.white)
            val drawable = ContextCompat.getDrawable(this@UpdateShoppingActivity, R.drawable.ic_arrow_back)
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                setHomeAsUpIndicator(drawable)
            }
            binding.toolbar.setTitleTextColor(Color.WHITE)
        }
    }

    private fun getDataFromIntent() {
        val receivedIntent = intent
        from = receivedIntent.getStringExtra("from").toString()
        if (receivedIntent != null && receivedIntent.hasExtra("update_data")) {
            val edit_data = receivedIntent.getSerializableExtra("update_data")
            if (edit_data != null) {
                shoppingListWithProducts = edit_data as ShoppingListWithProducts
            }
        }

        setData()
    }

    private fun setData() {

        if(from == "complete") {
            binding.btnSubmit.visibility = View.GONE
            binding.llOne.visibility = View.GONE
        }

        binding.etName.setText(shoppingListWithProducts.shoppingList.name)
        shoppingProducts = shoppingListWithProducts.products

        shoppingStatusUpdateAdapter = ShoppingStatusUpdateAdapter(this@UpdateShoppingActivity, from)

        shoppingStatusUpdateAdapter.setComponents(shoppingProducts)

        binding.recyclerView.adapter = shoppingStatusUpdateAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        shoppingViewModel = ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]

        binding.btnSubmit.setOnClickListener {
            val isEmpty = CollectionUtils.isEmpty(ShoppingStatusUpdateAdapter.selectedList)

            if(isEmpty) {
                Toast.makeText(this@UpdateShoppingActivity, "You haven't finished this shopping task; please click the back button.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val list = ShoppingStatusUpdateAdapter.selectedList!!
                val selectedList: ArrayList<ShoppingProducts> = arrayListOf()
                for (item in list) {
                    selectedList.add(
                        ShoppingProducts(
                            id = item.id,
                            isSuccessful = item.isSuccessful,
                            description = item.description,
                            category = item.category,
                            image = item.image,
                            price = item.price,
                            shoppingListId = 0,
                            title = item.title
                        )
                    )
                }
                val shopp: List<ShoppingProducts> = selectedList
                val existingShoppingListId = shoppingListWithProducts.shoppingList.id
                val newShoppingList = ShoppingList(id = existingShoppingListId, name = shoppingListWithProducts.shoppingList.name, date = shoppingListWithProducts.shoppingList.date)
                shoppingViewModel.updateShoppingListWithProducts(newShoppingList, shopp)
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
        if(from == "complete")
            finish()
        else
            showExitDialog()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this@UpdateShoppingActivity)
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