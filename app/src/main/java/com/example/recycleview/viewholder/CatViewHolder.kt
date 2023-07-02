package com.example.recycleview.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat

import com.example.recycleview.ImageLoader
import com.example.recycleview.model.ListItemUiModel

import com.example.recycleview.R

import com.example.recycleview.model.CatBreed
import com.example.recycleview.model.CatUiModel
import com.example.recycleview.model.Gender

private val FEMALE_SYMBOL by lazy {
    HtmlCompat.fromHtml("&#9793;", HtmlCompat.FROM_HTML_MODE_LEGACY)
}
private val MALE_SYMBOL by lazy {
    HtmlCompat.fromHtml("&#9794;", HtmlCompat.FROM_HTML_MODE_LEGACY)
}
private const val UNKNOWN_SYMBOL = "?"


class CatViewHolder(
    private val containerView: View,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : ListItemViewHolder(containerView) {
    private val catBiographyView: TextView by lazy {
        containerView
            .findViewById(R.id.item_cat_biography)
    }
    private val catBreedView: TextView by lazy {
        containerView.findViewById(R.id.item_cat_breed)
    }
    private val catGenderView: TextView by lazy {
        containerView.findViewById(R.id.item_cat_gender)
    }
    private val catNameView: TextView by lazy {
        containerView.findViewById(R.id.item_cat_name)
    }
    private val catPhotoView: ImageView by lazy {
        containerView.findViewById(R.id.item_cat_photo)
    }


    fun bindData(cat: CatUiModel) {

        containerView.setOnClickListener {
            onClickListener.onClick(cat)
        }

        imageLoader.loadImage(cat.imageUrl, catPhotoView)
        catNameView.text = cat.name.toString()
        catBreedView.text = when (cat.breed) {
            CatBreed.AmericanCurl.toString() -> "American Curl"
            CatBreed.BalineseJavanese.toString() -> "Balinese Javanese"
            CatBreed.ExoticShorthaired.toString() -> "Exotic Shorthaired"
            else -> {
                "Unknown Breed"
            }
        }

        catBiographyView.text = cat.biography
        catGenderView.text = when (cat.gender) {
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            else -> UNKNOWN_SYMBOL
        }
    }

    interface OnClickListener {
        fun onClick(catData: CatUiModel)
    }

    override fun bindData(listItem: ListItemUiModel) {
        require(listItem is ListItemUiModel.Cat)
        { "Expected ListItemUiModel.Cat" }
        val cat = listItem.cat
    }
}