package com.sts.o6uAttendance.ui.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

//    @JvmStatic
//    @BindingAdapter("bind:image_url")
//    fun loadImage(imageView: ImageView, url: String) {
//        Picasso.with(imageView.context).load(url).into(imageView)
//    }

@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).setData(data)
    }
}

