package com.luc.phonespecs.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luc.common.model.phonespecs.PhoneDetail
import com.luc.phonespecs.R
import com.luc.phonespecs.databinding.PhoneDetailItem3Binding
import com.luc.phonespecs.utils.getDrawableOrNull

class SearchResultAdapter :
    ListAdapter<PhoneDetail, SearchResultAdapter.ViewHolder>(PhonesDiffCallback) {

    private var onClick: ((PhoneDetail) -> Unit)? = null

    fun setOnClick(listener: (PhoneDetail) -> Unit) {
        onClick = listener
    }

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.phone_detail_item_3, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phoneDetail = currentList[position]
        holder.bind(phoneDetail)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PhoneDetailItem3Binding.bind(view)
        fun bind(phoneDetail: PhoneDetail) = with(binding) {
            binding.phoneDetail = phoneDetail
            if (phoneDetail.software?.os != "Android") {
                osImage.background = osImage.context.getDrawableOrNull(R.drawable.ic_apple)
            }
            os.text = "OS ${phoneDetail.software?.os?: "Not Provided"}"
            internal.text = "Internal Memory ${phoneDetail.hardware?.memory?.internal?.get(0)}"
            price.text = "Global Price ${phoneDetail.price?.get(0)?: "Not Provided"}"
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

