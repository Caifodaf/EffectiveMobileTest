package ru.android.effectivemobiletest.ui.product.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.android.effectivemobiletest.ui.product.adapter.ui.DetailsViewPager2
import ru.android.effectivemobiletest.ui.product.adapter.ui.FeaturesViewPager2
import ru.android.effectivemobiletest.ui.product.adapter.ui.ShopViewPager2

class ViewPagerDetailsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object{
        const val SHOP_SCREEN = 0
        const val DETAILS_SCREEN = 1
        const val FEATURES_SCREEN = 2
    }

    var type: Int = SHOP_SCREEN

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            SHOP_SCREEN ->  ShopViewPager2()
            DETAILS_SCREEN -> DetailsViewPager2()
            FEATURES_SCREEN -> FeaturesViewPager2()
            else -> ShopViewPager2()
        }

        return fragment as Fragment
    }
}