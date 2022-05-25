package com.example.android_gruppe_6

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_gruppe_6.data_class.Harbour
import com.example.android_gruppe_6.harbourlist.HarbourlistRvAdapter

@BindingAdapter("listHarbourData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Harbour>?) {
    val adapter = recyclerView.adapter as HarbourlistRvAdapter
    adapter.submitList(data)
}