package com.surya.grocerytask.ui.complete

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.surya.grocerytask.R

class CompleteScreenFragment : Fragment() {

    companion object {
        fun newInstance() = CompleteScreenFragment()
    }

    private lateinit var viewModel: CompleteScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_complete_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompleteScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}