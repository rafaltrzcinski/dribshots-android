package com.rafaltrzcinski.dribshots.shots.list

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rafaltrzcinski.dribshots.R

open class ShotImageBinder {

    companion object {
        @JvmStatic
        @BindingAdapter("shotImage")
        fun setShotImage(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                    .load(imageUrl)
                    .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("authorAvatar")
        fun setAuthorAvatar(imageView: ImageView, imageUrl: String) {

        }
    }
}