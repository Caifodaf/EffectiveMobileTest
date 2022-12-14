package ru.android.effectivemobiletest.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import ru.android.effectivemobiletest.api.model.MainModelResponse
import ru.android.effectivemobiletest.models.CartModel
import ru.android.effectivemobiletest.models.ProductDetailsModel

interface ApiService {

    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getMainPage(): Response<MainModelResponse>

    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getDetailsProductPage(): Response<ProductDetailsModel>

    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun geCartPage(): Response<CartModel>


}