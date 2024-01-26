package com.surya.grocerytask.ui.todo

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.surya.grocerytask.adapter.TodoListAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.FragmentTodoScreenBinding
import com.surya.grocerytask.ui.todo.add_shopping.AddShoppingActivity
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import javax.inject.Inject

class TodoScreenFragment : Fragment() {

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

        todoListAdapter = TodoListAdapter()

        shoppingViewModel.shoppingListsWithProducts.observe(requireActivity(), Observer { shoppingListsWithProducts ->
            todoListAdapter.setComponents(shoppingListsWithProducts)
            Log.e("*****",""+shoppingListsWithProducts.joinToString { x ->
                x.products.toString() +" ((())) "+ x.shoppingList.toString()
            })
        })

        binding.recyclerView.adapter = todoListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

}