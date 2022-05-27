package com.example.android_gruppe_6

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_gruppe_6.domain.Harbor
import com.example.android_gruppe_6.harborlist.HarborlistRvAdapter

@BindingAdapter("listHarbourData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Harbor>?) {
    val adapter = recyclerView.adapter as HarborlistRvAdapter
    adapter.submitList(data)
}