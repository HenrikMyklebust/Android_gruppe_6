package com.example.android_gruppe_6.harbourlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_gruppe_6.data_class.Harbour
import com.example.android_gruppe_6.databinding.RvHarbourlistItemholderBinding

class HarbourlistRvAdapter(private val onClickListener: OnClickListener) : ListAdapter<Harbour,
        HarbourlistRvAdapter.HarbourlistViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarbourlistViewHolder {
        return HarbourlistViewHolder(RvHarbourlistItemholderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HarbourlistViewHolder, position: Int) {
        val harbour = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(harbour)
        }
        holder.bind(harbour)
    }
    companion object DiffCallback: DiffUtil.ItemCallback<Harbour>() {
        override fun areItemsTheSame(oldItem: Harbour, newItem: Harbour): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Harbour, newItem: Harbour): Boolean {
            return oldItem.forApi == newItem.forApi
        }
    }
    class HarbourlistViewHolder(private var binding: RvHarbourlistItemholderBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(harbour: Harbour) {
            binding.harbour = harbour
            binding.geo = String.format("lat: %.4f long: %.4f", harbour.lat, harbour.long)
            binding.executePendingBindings()
        }
    }
    class OnClickListener(val clickListener: (harbour: Harbour) -> Unit) {
        fun onClick(harbour: Harbour) = clickListener(harbour)
    }
}