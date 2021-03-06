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
import com.luc.phonespecs.databinding.PhoneDetailItem2Binding

class BestCameraPhonesAdapter :
    ListAdapter<PhoneDetail, BestCameraPhonesAdapter.ViewHolder>(CalderaDiffCallback) {

    var phoneList: List<PhoneDetail> = listOf()

    private var onClick: ((PhoneDetail) -> Unit)? = null

    fun setOnClick(listener: (PhoneDetail) -> Unit) {
        onClick = listener
    }

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.phone_detail_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phoneDetail = currentList[position]
        holder.bind(phoneDetail)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PhoneDetailItem2Binding.bind(view)

        fun bind(phoneDetail: PhoneDetail) {
            binding.phoneDetail = phoneDetail
            binding.cameraDescription.text =
                phoneDetail.backCamera?.mp?.joinToString { s: String -> "$s | " }?.replace(",", "")
        }
    }

    object CalderaDiffCallback : DiffUtil.ItemCallback<PhoneDetail>() {
        override fun areItemsTheSame(oldItem: PhoneDetail, newItem: PhoneDetail): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PhoneDetail, newItem: PhoneDetail): Boolean {
            return oldItem == newItem
        }
    }

}

