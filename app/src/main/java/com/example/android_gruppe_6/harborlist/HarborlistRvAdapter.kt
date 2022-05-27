package com.example.android_gruppe_6.harborlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_gruppe_6.databinding.RvHarborlistItemholderBinding
import com.example.android_gruppe_6.domain.Harbor

class HarborlistRvAdapter(private val onClickListener: OnClickListener) : ListAdapter<Harbor,
        HarborlistRvAdapter.HarborlistViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarborlistViewHolder {
        return HarborlistViewHolder(RvHarborlistItemholderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HarborlistViewHolder, position: Int) {
        val harbour = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(harbour)
        }
        holder.bind(harbour)
    }
    companion object DiffCallback: DiffUtil.ItemCallback<Harbor>() {
        override fun areItemsTheSame(oldItem: Harbor, newItem: Harbor): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Harbor, newItem: Harbor): Boolean {
            return oldItem.apiName == newItem.apiName
        }
    }
    class HarborlistViewHolder(private var binding: RvHarborlistItemholderBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(harbor: Harbor) {
            binding.harbor = harbor
            binding.geo = String.format("lat: %.4f long: %.4f", harbor.lat, harbor.lon)
            val preferenceManager = PreferenceManager.getDefaultSharedPreferences(itemView.context)
            if (preferenceManager.getBoolean("lonLat", true))
                binding.rvLatAndLong.isGone = true
            binding.executePendingBindings()
        }
    }
    class OnClickListener(val clickListener: (harbor: Harbor) -> Unit) {
        fun onClick(harbor: Harbor) = clickListener(harbor)
    }
}