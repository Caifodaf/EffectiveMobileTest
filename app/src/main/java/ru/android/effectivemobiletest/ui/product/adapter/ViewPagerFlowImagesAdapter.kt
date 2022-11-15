package ru.android.effectivemobiletest.ui.product.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.android.effectivemobiletest.R


class ViewPagerFlowImagesAdapter(val activity: FragmentActivity) : PagerAdapter()
    {
        private val itemViewModels = mutableListOf<String>()

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = ImageView(activity)
            view.load(itemViewModels[position]){
                placeholder(R.color.gray)
                transformations(RoundedCornersTransformation(100f))
            }

            container.addView(view)
            return view
        }

        override fun getCount(): Int = itemViewModels.size

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        fun setMain(list: List<String>) {
            itemViewModels.clear()
            itemViewModels.addAll(list)
            notifyDataSetChanged()
        }

    }