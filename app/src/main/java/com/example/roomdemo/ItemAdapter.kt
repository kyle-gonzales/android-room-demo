package com.example.roomdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.databinding.RecyclerviewItemBinding

class ItemAdapter(val items: ArrayList<EmployeeEntity>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem (record : EmployeeEntity) {
            binding.tvName.text = record.name
            binding.tvEmail.text = record.email

            if (this.layoutPosition % 2 == 0) {
                binding.wrapper.setBackgroundColor(ContextCompat.getColor(this.itemView.context, R.color.lightGray))
            } else {
                binding.wrapper.setBackgroundColor(ContextCompat.getColor(this.itemView.context, R.color.white))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.bindItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}