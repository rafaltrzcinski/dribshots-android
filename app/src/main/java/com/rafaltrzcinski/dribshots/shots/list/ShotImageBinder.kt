package com.rafaltrzcinski.dribshots.shots.list

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.rafaltrzcinski.dribshots.R

open class ShotImageBinder {

    companion object {
        @JvmStatic
        @BindingAdapter("shotImage")
        fun setShotImage(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("circularAvatar")
        fun setAuthorAvatar(imageView: ImageView, imageUrl: String) {
            val context = imageView.context
            Glide.with(context)
                    .load(imageUrl)
                    .asBitmap()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ic_dribble)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap) {
                            val roundedDrawable =
                                    RoundedBitmapDrawableFactory.create(context.resources, resource)
                            roundedDrawable.isCircular = true
                            imageView.setImageDrawable(roundedDrawable)
                        }
                    })
        }
    }
}