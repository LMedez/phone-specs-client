package com.luc.phonespecs.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.SimpleItemListBinding

class SimpleItemAdapter : ListAdapter<String, SimpleItemAdapter.ViewHolder>(ItemsDiffCallback) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SimpleItemListBinding.bind(view)

        fun bind(string: String) {
            binding.text.text = string
        }
    }

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.simple_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleItemAdapter.ViewHolder, position: Int) {
        val stringItem = currentList[position]
        holder.bind(stringItem)
    }

    object ItemsDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}