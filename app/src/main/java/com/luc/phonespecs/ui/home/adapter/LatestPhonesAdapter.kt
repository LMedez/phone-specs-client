package com.luc.phonespecs.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.PhoneDetailItem1Binding

class LatestPhonesAdapter :
    ListAdapter<PhoneDetail, LatestPhonesAdapter.ViewHolder>(PhonesDiffCallback) {

    private var onClick: ((PhoneDetail) -> Unit)? = null

    fun setOnClick(listener: (PhoneDetail) -> Unit) {
        onClick = listener
    }

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.phone_detail_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phoneDetail = currentList[position]
        holder.bind(phoneDetail)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PhoneDetailItem1Binding.bind(view)

        fun bind(phoneDetail: PhoneDetail) {
            binding.root.setOnClickListener {
                onClick?.let { click ->
                    click(phoneDetail)
                }
            }
            binding.phoneDetail = phoneDetail
        }
    }

    object PhonesDiffCallback : DiffUtil.ItemCallback<PhoneDetail>() {
        override fun areItemsTheSame(oldItem: PhoneDetail, newItem: PhoneDetail): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PhoneDetail, newItem: PhoneDetail): Boolean {
            return oldItem == newItem
        }
    }
}

