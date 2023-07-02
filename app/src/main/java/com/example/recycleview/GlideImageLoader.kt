package com.example.recycleview

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide


    class GlideImageLoader(context: Context) : ImageLoader {
        override fun loadImage(imageUrl: String, imageView: ImageView) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .into(imageView)
        }
    }
