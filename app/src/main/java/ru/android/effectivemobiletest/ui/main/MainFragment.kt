package ru.android.effectivemobiletest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.MainFragmentBinding
import ru.android.effectivemobiletest.models.BestSellerModel
import ru.android.effectivemobiletest.ui.main.adapters.category.CategoryAdapter
import ru.android.effectivemobiletest.ui.main.adapters.delegate.*
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.BestSellerGridLayoutItem
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.HotSalesHorizontalLayoutItem
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.ListItem
import ru.android.effectivemobiletest.ui.main.filtersheetpanel.FilterSheetPanel
import ru.android.effectivemobiletest.utilits.RecyclerViewClickListener
import ru.android.effectivemobiletest.utilits.SpaceItemDecoration

@AndroidEntryPoint
class MainFragment : Fragment(),RecyclerViewClickListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val categoryAdapter = CategoryAdapter(this)

    private val adapterDelegate = ListDelegationAdapter<List<ListItem>>(
        HomeDelegates.hotSalesHorizontalLayout(this),
        HomeDelegates.bestSellerGridLayout(this),
    )

    private lateinit var toast: Toast

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        observeList()
    }

    private fun observeList() {
        vm.list.observe(viewLifecycleOwner, Observer { list ->
            //vm.loadHotSalesAdapter(hotSalesAdapter,list)
            adapterDelegate.apply {
                items = listOf(
                    HotSalesHorizontalLayoutItem(list.hotList),
                    BestSellerGridLayoutItem(list.bestList),
                )
                notifyDataSetChanged()
            }

            binding.progressBar.visibility = View.GONE
        })


        vm.error.observe(viewLifecycleOwner, Observer { error ->
            //todo
        })
    }

    private fun initUI(){

        initBottomNavigation()

        initRecyclerViewCategory()

        initDelegateRecyclerView()

       //initPagerView2HotSales()
       //initRecyclerViewBestSeller()

        initButtons()
    }

    private fun initDelegateRecyclerView() {
        binding.HomeDelegateRecyclerView.apply {
            adapter = adapterDelegate
            itemAnimator = null
        }
    }

    private fun initRecyclerViewCategory() {
        binding.CategoryRecyclerView.apply {
            addItemDecoration(SpaceItemDecoration(27))
            adapter = categoryAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

       vm.loadCategoryAdapter(categoryAdapter)
    }

    private fun initButtons() {

        buttonChangeRegion()
        buttonFilter()
        buttonSearchFilter()
        buttonMoreContentCategory()
    }

    private fun buttonChangeRegion(){
        binding.LLGeoPositionSelect.setOnClickListener{
            // Todo: Add fun change region
            toast = Toast.makeText(requireContext(), "Click change location", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
    private fun buttonFilter(){
        binding.ImageBtnFilter.setOnClickListener{
            showFilterBottomSheet()
        }
    }
    private fun buttonMoreContentCategory(){
        binding.TVBtnViewAllCategory.setOnClickListener{
            // Todo: Add navigate to all category
            toast = Toast.makeText(requireContext(), "Click all category", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
    private fun buttonSearchFilter(){
        binding.ImageBtnSortSearch.setOnClickListener{
            // Todo: Add fun filter search
            toast = Toast.makeText(requireContext(), "Click filter search", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
//    private fun buttonMoreContentHotSales(){
//        binding.TVBtnSeeMoreHotSales.setOnClickListener{
//            // Todo: Add navigate to more hot sales
//            toast = Toast.makeText(requireContext(), "Click more hot sales", Toast.LENGTH_SHORT)
//            toast.show()
//        }
//    }
//    private fun buttonMoreContentBestSeller(){
//        binding.TVBtnSeeMoreBestSeller.setOnClickListener{
//            // Todo: Add navigate to more best seller
//            toast = Toast.makeText(requireContext(), "Click more best seller", Toast.LENGTH_SHORT)
//            toast.show()
//        }
//    }

    private fun showFilterBottomSheet(){
        val filterSheetPanel = FilterSheetPanel(vm)
        filterSheetPanel.show(childFragmentManager,filterSheetPanel.tag)
    }

    private fun initBottomNavigation() {
        binding.ImageBottomBtnCart.setOnClickListener{
            findNavController().navigate(R.id.cartFragment, null)
        }
        binding.ImageBottomBtnFavorite.setOnClickListener{
            // Todo: navigate to favorite page
            //findNavController().navigate(R.id.action_mainFragment_to_favoriteFragment, null)
        }
        binding.ImageBottomBtnProfile.setOnClickListener{
            //findNavController().navigate(R.id.action_mainFragment_to_profileFragment, null)
        }
    }


    override fun onRecyclerViewItemClick(view: View, list: Any?) {
        when(view.id){
            R.id.CCCategoryMain ->{
                // Todo: Add item category fun
                toast = Toast.makeText(requireContext(), "Click item category", Toast.LENGTH_SHORT)
                toast.show()
            }
            R.id.MainBlockBestProductItem ->{
                findNavController().navigate(R.id.productPageFragment, null)
            }
            R.id.MainBlockHotItem ->{
                // Todo: Add item fun
                toast = Toast.makeText(requireContext(), "Click item hot product", Toast.LENGTH_SHORT)
                toast.show()
            }
            R.id.ImageBtnFavorite ->{
                // Todo: Add item fun
                toast = Toast.makeText(requireContext(), "Click item favorite", Toast.LENGTH_SHORT)
                toast.show()
//                val image = view as ImageView
//                val drawableFill = requireContext().resources.getDrawable(R.drawable.ic_favorite_fill,requireContext().theme)
//                val drawableEmpty = requireContext().resources.getDrawable(R.drawable.ic_favorite,requireContext().theme)
//
//                when((list as BestSellerModel).isFavorite){
//                    true-> image.setImageDrawable(drawableEmpty)
//                    false-> image.setImageDrawable(drawableFill)
//                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        toast.cancel()
    }
}