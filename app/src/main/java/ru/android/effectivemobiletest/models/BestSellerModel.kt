package ru.android.effectivemobiletest.models

import com.squareup.moshi.Json
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.ListItem

data class BestSellerModel (
    @Json(name = "id")val id: Int,
    @Json(name = "is_favorites")val isFavorite: Boolean,
    @Json(name = "title")val title: String,
    @Json(name = "price_without_discount")val price: Int,
    @Json(name = "discount_price")val priceDiscount: Int,
    @Json(name = "picture")val imageUrl: String,
): ListItem