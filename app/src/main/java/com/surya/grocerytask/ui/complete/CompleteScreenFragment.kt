package com.surya.grocerytask.ui.complete

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.surya.grocerytask.R
import com.surya.grocerytask.adapter.TodoListAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.FragmentCompleteScreenBinding
import com.surya.grocerytask.databinding.FragmentPendingScreenBinding
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.ui.todo.add_shopping.EditShoppingActivity
import com.surya.grocerytask.ui.todo.add_shopping.UpdateShoppingActivity
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import javax.inject.Inject

class CompleteScreenFragment : Fragment(), TodoListAdapter.OnItemClick {
    private lateinit var binding: FragmentCompleteScreenBinding
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
        binding = FragmentCompleteScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        shoppingViewModel = ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]

        todoListAdapter = TodoListAdapter( this@CompleteScreenFragment, "complete")

        shoppingViewModel.getListsFullyComplete().observe(requireActivity(), Observer { shoppingListsWithProducts ->
            todoListAdapter.setComponents(shoppingListsWithProducts)
        })

        binding.recyclerView.adapter = todoListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onItemClicked(shoppingListWithProducts: ShoppingListWithProducts, action: String) {
        if (action == "view") {
            val intent = Intent(requireActivity(), UpdateShoppingActivity::class.java)
            intent.putExtra("update_data", shoppingListWithProducts)
            intent.putExtra("from", "complete")
            startActivity(intent)
        }
    }

}