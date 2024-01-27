package com.surya.grocerytask.ui.pending

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.surya.grocerytask.adapter.TodoListAdapter
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.FragmentPendingScreenBinding
import com.surya.grocerytask.model.ShoppingListWithProducts
import com.surya.grocerytask.ui.todo.add_shopping.UpdateShoppingActivity
import com.surya.grocerytask.viewmodel.ShoppingViewModel
import com.surya.grocerytask.viewmodel.ShoppingViewModelFactory
import javax.inject.Inject

class PendingScreenFragment : Fragment(), TodoListAdapter.OnItemClick {
    private lateinit var binding: FragmentPendingScreenBinding

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
    ): View {
        binding = FragmentPendingScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        shoppingViewModel =
            ViewModelProvider(this, shoppingViewModelFactory)[ShoppingViewModel::class.java]

        todoListAdapter = TodoListAdapter(this@PendingScreenFragment, "pending")


        shoppingViewModel.getListsFullyCompleteAndHalfPending()
            .observe(requireActivity(), Observer { shoppingListsWithProducts ->
                todoListAdapter.setComponents(shoppingListsWithProducts)
            })

        binding.recyclerView.adapter = todoListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onItemClicked(shoppingListWithProducts: ShoppingListWithProducts, action: String) {
        if (action == "view") {
            val intent = Intent(requireActivity(), UpdateShoppingActivity::class.java)
            intent.putExtra("update_data", shoppingListWithProducts)
            intent.putExtra("from", "pending")
            startActivity(intent)
        }
    }

}