package com.surya.grocerytask.ui.todo

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.surya.grocerytask.R
import com.surya.grocerytask.databinding.FragmentTodoScreenBinding
import com.surya.grocerytask.ui.todo.add_shopping.AddShoppingActivity

class TodoScreenFragment : Fragment() {

    private lateinit var binding: FragmentTodoScreenBinding

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

    }

}