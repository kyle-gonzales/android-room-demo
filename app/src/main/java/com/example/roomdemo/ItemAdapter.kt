package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.RecyclerviewItemBinding

class ItemAdapter(private val items: ArrayList<EmployeeEntity>,
                  private val updateListener: (id: Int)->Unit,
                  private val deleteListener: (id: Int) -> Unit,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // view holder holds the items in the items in the recyclerView
        var wrapper = binding.wrapper
        var tvName = binding.tvName
        var tvEmail = binding.tvEmail
        var ivEdit = binding.ivEdit
        var ivDelete = binding.ivDelete
//        fun bindItem (record : EmployeeEntity) {
//            binding.tvName.text = record.name
//            binding.tvEmail.text = record.email
//
//            if (this.layoutPosition % 2 == 0) {
//                binding.wrapper.setBackgroundColor(ContextCompat.getColor(this.itemView.context, R.color.lightGray))
//            } else {
//                binding.wrapper.setBackgroundColor(ContextCompat.getColor(this.itemView.context, R.color.white))
//            }
//
//            binding.ivEdit.setOnClickListener {
////                updateListener.invoke(record.id)
//            }
//
//            binding.ivDelete.setOnClickListener {
////                deleteListener.invoke(record.id)
//            }
//
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvEmail.text = item.email
        if (position % 2 == 0) {
                holder.wrapper.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
            } else {
                holder.wrapper.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }

            holder.ivEdit.setOnClickListener {
                updateListener.invoke(item.id)
            }

            holder.ivDelete.setOnClickListener {
                deleteListener.invoke(item.id)
            }


//        holder.bindItem(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}