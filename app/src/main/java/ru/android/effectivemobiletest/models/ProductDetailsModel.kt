package ru.android.effectivemobiletest.models

import com.squareup.moshi.Json

data class ProductDetailsModel (
    @Json(name = "id")val id: Int,
    @Json(name = "isFavorites")val isFavorite: Boolean,
    @Json(name = "title")val title: String,
    @Json(name = "rating")val rating: Float?,
    @Json(name = "images")val imagesList: List<String>?,
    @Json(name = "price")val price: Int,
    @Json(name = "sd")val storageDefault: String,
    @Json(name = "ssd")val ramDefault: String,
    @Json(name = "CPU")val cpuName: String,
    @Json(name = "camera")val cameraType: String,
    @Json(name = "capacity")val capacityList: List<String>,
    @Json(name = "color")val colorList: List<String>,
)