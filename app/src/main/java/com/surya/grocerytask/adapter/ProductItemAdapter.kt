package com.surya.grocerytask.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surya.grocerytask.R
import com.surya.grocerytask.databinding.LytProductItemsBinding
import com.surya.grocerytask.model.ProductList
import com.surya.grocerytask.ui.todo.add_shopping.AddShoppingActivity
import com.surya.grocerytask.viewmodel.ProductItemViewModel

class ProductItemAdapter(private val activity: Activity) : RecyclerView.Adapter<ProductItemAdapter.ViewHolder>() {

    private var components: List<ProductList> = emptyList()

    companion object {
        var selectedList: ArrayList<ProductList>? = arrayListOf()
    }

    init {
        selectedList?.addAll(components)
    }

    inner class ViewHolder(private val binding: LytProductItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(component: ProductList) {
            binding.apply {

                Glide.with(activity)
                    .load(component.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.ivProduct)

                binding.tvProductName.text = component.title
                binding.cbProduct.isChecked = component.isChecked

                binding.cbProduct.setOnCheckedChangeListener { _, isChecked ->
                    component.isChecked = isChecked
                    selectedList?.clear()
                    selectedList?.addAll(components)
                    //productViewModel.insertComponent(component) // Update the checked state in the database
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LytProductItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(components[position])
    }

    override fun getItemCount(): Int {
        return components.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setComponents(components: List<ProductList>) {
        this.components = components
        notifyDataSetChanged()
    }

}