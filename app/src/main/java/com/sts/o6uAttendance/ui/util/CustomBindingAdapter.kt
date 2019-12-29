package com.sts.o6uAttendance.ui.util

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

object CustomBindingAdapter{

    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView, url: String) {
        Picasso.with(imageView.context).load(url).into(imageView)
    }

}