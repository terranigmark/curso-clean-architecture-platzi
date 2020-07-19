package com.platzi.android.rickandmorty.imagemanager

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.bindCircularImageUrl(url: String?, @DrawableRes placeholder: Int,
                                   @DrawableRes errorPlaceholder: Int) {
    if (url.isNullOrBlank()) {
        setImageResource(placeholder)
        return
    }

    Glide.with(context)
        .load(url)
        .error(errorPlaceholder)
        .placeholder(placeholder)
        .circleCrop()
        .into(this)
}

fun ImageView.bindImageUrl(url: String?, @DrawableRes placeholder: Int,
                           @DrawableRes errorPlaceholder: Int) {
    if (url.isNullOrBlank()) {
        setImageResource(placeholder)
        return
    }

    Glide.with(context)
        .load(url)
        .error(errorPlaceholder)
        .placeholder(placeholder)
        .into(this)
}
