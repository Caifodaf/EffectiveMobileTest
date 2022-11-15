package ru.android.effectivemobiletest.localdata

import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.models.CategoryModel

class DataCategory {

    fun setDataCategory(list: MutableList<CategoryModel>): MutableList<CategoryModel>{
        list.add(
            CategoryModel(
                id = 0,
                image = (R.drawable.ic_phones),
                title = "Phones",
                select = true
            ))
        list.add(
            CategoryModel(
                id = 1,
                image = (R.drawable.ic_computer),
                title = "Computer",
                select = true
            ))
        list.add(
            CategoryModel(
                id = 2,
                image = (R.drawable.ic_health),
                title = "Health",
                select = true
            ))
        list.add(
            CategoryModel(
                id = 3,
                image = (R.drawable.ic_books),
                title = "Books",
                select = true
            ))

        return list
    }
}