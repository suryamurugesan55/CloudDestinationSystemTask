package com.surya.grocerytask.ui.todo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.surya.grocerytask.adapter.TodoListAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.FragmentTodoScreenBinding
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.ui.todo.add_shopping.AddShoppingActivity
import com.surya.grocerytask.ui.todo.add_shopping.EditShoppingActivity
import com.surya.grocerytask.ui.todo.add_shopping.UpdateShoppingActivity
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import java.util.Calendar
import javax.inject.Inject

class TodoScreenFragment : Fragment(), TodoListAdapter.OnItemClick {

    private lateinit var binding: FragmentTodoScreenBinding

    private lateinit var shoppingViewModel: ShoppingViewModel
    @Inject
    lateinit var shoppingViewModelFactory: ShoppingViewModelFactory

    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as BaseApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.fab.setOnClickListener{
            val intent = Intent(requireContext(), AddShoppingActivity::class.java)
            startActivity(intent)
        }

        shoppingViewModel = ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]

        todoListAdapter = TodoListAdapter(this@TodoScreenFragment, "todo")

        shoppingViewModel.getListsNotFullyComplete().observe(requireActivity(), Observer { shoppingListsWithProducts ->
            todoListAdapter.setComponents(shoppingListsWithProducts)
        })

        binding.recyclerView.adapter = todoListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    @SuppressLint("NewApi")
    override fun onItemClicked(shoppingListWithProducts: ShoppingListWithProducts, action: String) {
        if(action == "edit") {
            AlertDialog.Builder(requireContext())
                .setMessage("Do you want to edit this shopping items?")
                .setTitle("Alert!")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(requireActivity(), EditShoppingActivity::class.java)
                    intent.putExtra("edit_data", shoppingListWithProducts)
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        } else if (action == "view") {
            val intent = Intent(requireActivity(), UpdateShoppingActivity::class.java)
            intent.putExtra("update_data", shoppingListWithProducts)
            intent.putExtra("from", "todo")
            startActivity(intent)
        } else if (action == "reminder_set") {
            Toast.makeText(requireContext(), "Already reminder set on this shopping", Toast.LENGTH_SHORT).show()
        } else if (action == "reminder") {
            AlertDialog.Builder(requireContext())
                .setMessage("Do you want to add reminder to this shopping?")
                .setTitle("Alert!")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    getIntervalAlert(shoppingListWithProducts)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    @SuppressLint("NewApi")
    private fun getIntervalAlert(shoppingListWithProducts: ShoppingListWithProducts) {
        val items = arrayOf("After 1 mins","After 5 mins", "After 10 mins", "After 15 mins")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Time Interval")
        builder.setItems(items) { dialog: DialogInterface?, which: Int ->
            val selectedItem = items[which]
            when (selectedItem) {
                "After 1 mins" -> {
                    val currentTime = Calendar.getInstance()
                    currentTime.add(Calendar.MINUTE, 1)
                    val time = currentTime.toInstant().toEpochMilli()

                    shoppingViewModel.setReminderForShoppingList(
                        shoppingListId = shoppingListWithProducts.shoppingList.id,
                        reminderTime = time
                    )
                }
                "After 5 mins" -> {
                    val currentTime = Calendar.getInstance()
                    currentTime.add(Calendar.MINUTE, 5)
                    val time = currentTime.toInstant().toEpochMilli()

                    shoppingViewModel.setReminderForShoppingList(
                        shoppingListId = shoppingListWithProducts.shoppingList.id,
                        reminderTime = time
                    )
                }
                "After 10 mins" -> {
                    val currentTime = Calendar.getInstance()
                    currentTime.add(Calendar.MINUTE, 10)
                    val time = currentTime.toInstant().toEpochMilli()

                    shoppingViewModel.setReminderForShoppingList(
                        shoppingListId = shoppingListWithProducts.shoppingList.id,
                        reminderTime = time
                    )
                }
                "After 15 mins" -> {
                    val currentTime = Calendar.getInstance()
                    currentTime.add(Calendar.MINUTE, 15)
                    val time = currentTime.toInstant().toEpochMilli()

                    shoppingViewModel.setReminderForShoppingList(
                        shoppingListId = shoppingListWithProducts.shoppingList.id,
                        reminderTime = time
                    )
                }
                else -> {
                    val currentTime = Calendar.getInstance()
                    currentTime.add(Calendar.MINUTE, 1)
                    val time = currentTime.toInstant().toEpochMilli()

                    shoppingViewModel.setReminderForShoppingList(
                        shoppingListId = shoppingListWithProducts.shoppingList.id,
                        reminderTime = time
                    )
                }
            }
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

}