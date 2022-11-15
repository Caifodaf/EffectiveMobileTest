package ru.android.effectivemobiletest.api.model

import com.squareup.moshi.Json
import ru.android.effectivemobiletest.models.BestSellerModel
import ru.android.effectivemobiletest.models.HotSalesModel

data class MainModelResponse(
    @Json(name = "home_store") val hotList: List<HotSalesModel>,
    @Json(name = "best_seller") val bestList: List<BestSellerModel>,
)
