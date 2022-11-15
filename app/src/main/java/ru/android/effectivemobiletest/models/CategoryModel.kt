package ru.android.effectivemobiletest.models

data class CategoryModel(
    val id: Int,
    val image: Int,
    val title: String,
    val select: Boolean = false,
)
