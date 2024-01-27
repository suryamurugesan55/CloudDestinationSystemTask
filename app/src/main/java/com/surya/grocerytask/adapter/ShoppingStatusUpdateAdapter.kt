package com.surya.grocerytask.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surya.grocerytask.R
import com.surya.grocerytask.databinding.LytProductItemsBinding
import com.surya.grocerytask.model.ShoppingProducts

class ShoppingStatusUpdateAdapter(private val activity: Activity, private val from: String) : RecyclerView.Adapter<ShoppingStatusUpdateAdapter.ViewHolder>() {

    private var components: List<ShoppingProducts> = emptyList()

    init {
        selectedList?.addAll(components)
    }

    companion object {
        var selectedList: ArrayList<ShoppingProducts>? = arrayListOf()
    }

    inner class ViewHolder(private val binding: LytProductItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(component: ShoppingProducts) {
            binding.apply {

                Glide.with(activity)
                    .load(component.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.ivProduct)

                binding.tvProductName.text = component.title
                binding.cbProduct.isChecked = component.isSuccessful

                if(from == "complete") {
                    binding.cbProduct.visibility = View.GONE
                }

                binding.cbProduct.setOnCheckedChangeListener { _, isChecked ->
                    component.isSuccessful = isChecked
                    selectedList?.clear()
                    selectedList?.addAll(components)
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setComponents(components: List<ShoppingProducts>) {
        this.components = components
        selectedList?.clear()
        selectedList?.addAll(components)
        notifyDataSetChanged()
    }

}