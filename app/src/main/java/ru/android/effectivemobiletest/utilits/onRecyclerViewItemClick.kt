package ru.android.effectivemobiletest.utilits

import android.view.View

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, list: Any?)
}