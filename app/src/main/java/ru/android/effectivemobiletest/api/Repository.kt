package ru.android.effectivemobiletest.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import ru.android.effectivemobiletest.api.init.RetrofitClient
import ru.android.effectivemobiletest.api.model.MainModelResponse
import ru.android.effectivemobiletest.models.CartModel
import ru.android.effectivemobiletest.models.ProductDetailsModel
import javax.inject.Inject

class Repository @Inject constructor(){

    suspend fun getMainPage(): Flow<Response<MainModelResponse>> {
        return flow {
            emit(RetrofitClient.api.getMainPage())
        }
    }

    suspend fun getDetailsProductPage(): Flow<Response<ProductDetailsModel>> {
        return flow {
            emit(RetrofitClient.api.getDetailsProductPage())
        }
    }

    suspend fun geCartPage(): Flow<Response<CartModel>> {
        return flow {
            emit(RetrofitClient.api.geCartPage())
        }
    }
}