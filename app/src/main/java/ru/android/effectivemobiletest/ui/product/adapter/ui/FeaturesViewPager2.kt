package ru.android.effectivemobiletest.ui.product.adapter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import ru.android.effectivemobiletest.databinding.LayoutShopPagerBinding
import ru.android.effectivemobiletest.databinding.ProductPageFragmentBinding
import ru.android.effectivemobiletest.ui.product.ProductPageViewModel
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerDetailsAdapter

@AndroidEntryPoint
class FeaturesViewPager2 : Fragment() {

    private var _binding: LayoutShopPagerBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<ProductPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutShopPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var toast: Toast

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}