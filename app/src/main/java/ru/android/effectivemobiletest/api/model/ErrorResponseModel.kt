package ru.android.effectivemobiletest.api.model

import com.squareup.moshi.Json

data class ErrorResponse(
    var codeApp:Int,
    var message:String? = "",
    var codeApi:Int,
)

