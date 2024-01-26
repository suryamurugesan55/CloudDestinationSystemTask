package com.surya.grocerytask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surya.grocerytask.databinding.LytTodoItemBinding
import com.surya.grocerytask.model.ShoppingListWithProducts
import java.text.SimpleDateFormat
import java.util.Date

class TodoListAdapter() : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    private var components: List<ShoppingListWithProducts> = emptyList()

    inner class ViewHolder(private val binding: LytTodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(component: ShoppingListWithProducts) {
            binding.apply {

                binding.tvTodoName.text = component.shoppingList.name
                binding.tvProductDate.text = "Created at : "+convertLongToTime(component.shoppingList.date)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd-MMMM-yyy hh:mm a")
        return format.format(date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LytTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(components[position])
    }

    override fun getItemCount(): Int {
        return components.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setComponents(components: List<ShoppingListWithProducts>) {
        this.components = components
        notifyDataSetChanged()
    }

}