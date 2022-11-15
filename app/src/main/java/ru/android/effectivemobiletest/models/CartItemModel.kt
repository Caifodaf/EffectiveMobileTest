package ru.android.effectivemobiletest.models

import com.squareup.moshi.Json

data class CartItemModel (
    @Json(name = "id")val id: Int,
    @Json(name = "title")val title: String,
    @Json(name = "price")val priceDiscount: Int,
    @Json(name = "images")val imageUrl: String,
)