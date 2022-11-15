package ru.android.effectivemobiletest.models

import com.squareup.moshi.Json
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.ListItem

data class HotSalesModel (
    @Json(name = "id")val id: Int,
    @Json(name = "is_new")val isNew: Boolean? = false,
    @Json(name = "title")val title: String,
    @Json(name = "subtitle")val subTitle: String,
    @Json(name = "picture")val imageUrl: String,
    @Json(name = "is_buy")val isBuy: Boolean,
): ListItem