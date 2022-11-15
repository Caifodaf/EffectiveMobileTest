package ru.android.effectivemobiletest.models

import com.squareup.moshi.Json

data class CartModel (
    @Json(name = "basket")val basket: List<CartItemModel>,
    @Json(name = "delivery")val deliveryCost: String,
    @Json(name = "id")val id: String,
    @Json(name = "total")val total: Int,
)