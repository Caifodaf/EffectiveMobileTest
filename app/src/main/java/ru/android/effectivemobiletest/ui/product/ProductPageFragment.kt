package ru.android.effectivemobiletest.ui.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import me.crosswall.lib.coverflow.CoverFlow
import me.crosswall.lib.coverflow.core.PagerContainer
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.ProductPageFragmentBinding
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerDetailsAdapter
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerDetailsAdapter.Companion.DETAILS_SCREEN
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerDetailsAdapter.Companion.FEATURES_SCREEN
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerDetailsAdapter.Companion.SHOP_SCREEN
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerFlowImagesAdapter
import ru.android.effectivemobiletest.utilits.ViewPager2ViewHeightAnimator


@AndroidEntryPoint
class ProductPageFragment : Fragment() {

    companion object{
        const val FAVORITE_TRUE = true
        const val FAVORITE_FALSE = false
    }

    private var _binding: ProductPageFragmentBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<ProductPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var toast: Toast
    private lateinit var pagerViewDetails: ViewPager2
    private lateinit var viewPagerDetailsAdapter: ViewPagerDetailsAdapter

    private lateinit var pagerContainerViewImages: PagerContainer
    private lateinit var pagerViewImages: ViewPager
    private lateinit var viewPagerFlowImagesAdapter: ViewPagerFlowImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        observeList()
    }

    private fun initUI(){

        initButtons()

        initPagerView2Details()
    }

    @SuppressLint("SetTextI18n")
    private fun observeList(){
        vm.dataDetailsPage.observe(viewLifecycleOwner, Observer { list ->

            initPagerFlow()

            vm.loadImagesAdapter(viewPagerFlowImagesAdapter, list.imagesList!!)

            pagerViewImages.currentItem = 2

            binding.TVTitleProduct.text = list.title
            if (list.rating != null)
                binding.RatingBar.rating = list.rating
            binding.TVPriceProduct.text =
                requireContext().getString(R.string.dollar_price) +
                        list.price.toString()

            changeFavoriteView(list.isFavorite)

        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeFavoriteView(type: Boolean = false) {
        when(type){
            FAVORITE_TRUE ->
                binding.ImageBtnFavorite.setImageDrawable(
                    requireContext().getDrawable(R.drawable.ic_favorite_fill))
            FAVORITE_FALSE -> {
                binding.ImageBtnFavorite.setImageDrawable(
                    requireContext().getDrawable(R.drawable.ic_favorite))
            }
        }

    }

    @SuppressLint("ResourceType")
    private fun initPagerFlow(){
        viewPagerFlowImagesAdapter = ViewPagerFlowImagesAdapter(requireActivity())
        pagerContainerViewImages = binding.CowerFlowImage

        pagerViewImages = pagerContainerViewImages.viewPager
        pagerViewImages.adapter = viewPagerFlowImagesAdapter

        pagerViewImages.offscreenPageLimit = viewPagerFlowImagesAdapter.count
        pagerViewImages.clipChildren = false

        val intent = requireActivity().intent
        val showTransformer = intent.getBooleanExtra("showRotate", false)

        if (true) {
            CoverFlow.Builder()
                .with(pagerViewImages)
                .scale(0.2f)
                .pagerMargin(0f)
                .spaceSize(0f)
                .rotationY(0f)
                .build()
        } else {
            pagerViewImages.pageMargin = 5
        }


    }


    private fun initPagerView2Details(){
        pagerViewDetails = binding.ViewPager2DetailsProduct
        viewPagerDetailsAdapter = ViewPagerDetailsAdapter(this)
        pagerViewDetails = binding.ViewPager2DetailsProduct
        pagerViewDetails.adapter = viewPagerDetailsAdapter

        binding.TVTitlePagerShop.setOnClickListener {
            setColorBtn(binding.TVTitlePagerShop,binding.TVTitlePagerDetails,binding.TVTitlePagerFeatures)
            pagerViewDetails.currentItem = 0
        }
        binding.TVTitlePagerDetails.setOnClickListener {
            setColorBtn(binding.TVTitlePagerDetails,binding.TVTitlePagerShop,binding.TVTitlePagerFeatures)
            pagerViewDetails.currentItem = 1
        }
        binding.TVTitlePagerFeatures.setOnClickListener {
            setColorBtn(binding.TVTitlePagerFeatures,binding.TVTitlePagerShop,binding.TVTitlePagerDetails)
            pagerViewDetails.currentItem = 2
        }

        pagerViewDetails.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 ->{
                        setColorBtn(binding.TVTitlePagerShop,binding.TVTitlePagerDetails,binding.TVTitlePagerFeatures)
                        setVisibleBtn(binding.ViewIndicatorShop,binding.ViewIndicatorDetails,binding.ViewIndicatorFeatures)
                        viewPagerDetailsAdapter.type = SHOP_SCREEN}
                    1 ->{
                        setColorBtn(binding.TVTitlePagerDetails,binding.TVTitlePagerShop,binding.TVTitlePagerFeatures)
                        setVisibleBtn(binding.ViewIndicatorDetails,binding.ViewIndicatorShop,binding.ViewIndicatorFeatures)
                        viewPagerDetailsAdapter.type = DETAILS_SCREEN}
                    2 ->{
                        setColorBtn(binding.TVTitlePagerFeatures,binding.TVTitlePagerShop,binding.TVTitlePagerDetails)
                        setVisibleBtn(binding.ViewIndicatorFeatures,binding.ViewIndicatorDetails,binding.ViewIndicatorShop)
                        viewPagerDetailsAdapter.type = FEATURES_SCREEN}
                }
            }
        })

        val viewPager2ViewHeightAnimator = ViewPager2ViewHeightAnimator()
        viewPager2ViewHeightAnimator.viewPager2 = pagerViewDetails
    }

    private fun setColorBtn(viewSet: TextView, view2: TextView, view3: TextView){
        viewSet.setTextColor(activity?.getColor(R.color.dark_blue_original)!!)
        view2.setTextColor(activity?.getColor(R.color.alpha_white)!!)
        view3.setTextColor(activity?.getColor(R.color.alpha_white)!!)
    }

    private fun setVisibleBtn(viewSet: View, view2: View, view3: View){
        viewSet.visibility = View.VISIBLE
        view2.visibility = View.INVISIBLE
        view3.visibility = View.INVISIBLE
    }

    private fun initButtons(){
        buttonBack()
        buttonToCart()
        buttonFavorite()
        buttonAddToCart()

    }

    private fun buttonBack(){
        binding.ImageBtnBack.setOnClickListener{
            parentFragmentManager.popBackStack();
        }
    }

    private fun buttonToCart(){
        binding.ImageBtnCart.setOnClickListener{
            findNavController().navigate(R.id.action_productPageFragment_to_cartFragment, null)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun buttonFavorite(){
        binding.ImageBtnFavorite.setOnClickListener{
            when(binding.ImageBtnFavorite.drawable){
                requireContext().getDrawable(R.drawable.ic_favorite) ->
                    changeFavoriteView(FAVORITE_TRUE)
                requireContext().getDrawable(R.drawable.ic_favorite_fill) ->
                    changeFavoriteView(FAVORITE_FALSE)
            }
        }
    }

    private fun buttonAddToCart(){
        binding.LLBtnAddToCart.setOnClickListener{
            // Todo: Add fun api post add to cart
            toast = Toast.makeText(requireContext(), "Click add to your cart", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}