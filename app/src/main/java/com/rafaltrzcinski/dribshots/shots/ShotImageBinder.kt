package com.rafaltrzcinski.dribshots.shots

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rafaltrzcinski.dribshots.R

open class ShotImageBinder {

    companion object {
        @JvmStatic
        @BindingAdapter("shotImage")
        fun setShotImage(imageView: ImageView, imageUrl: String) {
            val context = imageView.context

            Glide.with(context)
                    .load(imageUrl)
                    .into(imageView)
        }
    }
}