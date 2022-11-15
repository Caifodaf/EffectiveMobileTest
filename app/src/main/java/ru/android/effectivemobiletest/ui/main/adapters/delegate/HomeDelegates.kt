package ru.android.effectivemobiletest.ui.main.adapters.delegate

import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import coil.load
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.BestSellerGridLayoutBinding
import ru.android.effectivemobiletest.databinding.BestSellerProductItemBinding
import ru.android.effectivemobiletest.databinding.HotSalesHorizontalLayoutBinding
import ru.android.effectivemobiletest.databinding.HotSalesItemBinding
import ru.android.effectivemobiletest.models.BestSellerModel
import ru.android.effectivemobiletest.models.HotSalesModel
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.BestSellerGridLayoutItem
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.HotSalesHorizontalLayoutItem
import ru.android.effectivemobiletest.ui.main.adapters.delegate.listitem.ListItem
import ru.android.effectivemobiletest.utilits.RecyclerViewClickListener

object HomeDelegates {

    // HotSales Pager2View Horizontal
    fun hotSalesHorizontalLayout(listenerClick: RecyclerViewClickListener) = adapterDelegateViewBinding<
            HotSalesHorizontalLayoutItem,
            ListItem,
            HotSalesHorizontalLayoutBinding>(
        // OnCreateViewHolder -> ItemView
        { inflater, container ->
            HotSalesHorizontalLayoutBinding.inflate(inflater, container, false ).apply {
                PagerHotSales.adapter = hotSalesHorizontalAdapter(listenerClick)
            }
        }
    ) {
        // OnBingVIewHolder
        bind {
            (binding.PagerHotSales.adapter as? ListDelegationAdapter<List<ListItem>>).apply {
                this!!.items = item.list
                notifyDataSetChanged()
            }
        }
    }

    private fun horizontalHotSalesDelegate(listenerClick: RecyclerViewClickListener) = adapterDelegateViewBinding<HotSalesModel, ListItem, HotSalesItemBinding>(
        { inflater, container -> HotSalesItemBinding.inflate(inflater, container,false)}
    ) {
        bind {
            binding.MainBlockHotItem.setOnClickListener {
                listenerClick.onRecyclerViewItemClick(binding.MainBlockHotItem, item)
            }

            binding.TvTitle.text = item.title
            binding.TvDescription.text = item.subTitle

            when (item.isNew) {
                true ->
                    binding.LLNewBlock.visibility = View.VISIBLE
                false ->
                    binding.LLNewBlock.visibility = View.GONE
            }

            binding.Image.load(item.imageUrl)
        }
    }

    private fun hotSalesHorizontalAdapter(listenerClick: RecyclerViewClickListener) = ListDelegationAdapter(
        horizontalHotSalesDelegate(listenerClick)
    )

    // BestSeller RecyclerView Grid

    fun bestSellerGridLayout(listenerClick: RecyclerViewClickListener) = adapterDelegateViewBinding<
            BestSellerGridLayoutItem,
            ListItem,
            BestSellerGridLayoutBinding>(
        // OnCreateViewHolder -> ItemView
        { inflater, container ->
            BestSellerGridLayoutBinding.inflate(inflater, container, false ).apply {
                BestSellerRecyclerVIew.adapter = bestSellerGridAdapter(listenerClick)
            }
        }
    ) {
        // OnBingVIewHolder
        bind {
            (binding.BestSellerRecyclerVIew.adapter as? ListDelegationAdapter<List<ListItem>>).apply {
                this!!.items = item.list
                notifyDataSetChanged()
            }
        }
    }

    private fun gridBestSellerDelegate(listenerClick: RecyclerViewClickListener) = adapterDelegateViewBinding<BestSellerModel, ListItem, BestSellerProductItemBinding>(
        { inflater, container -> BestSellerProductItemBinding.inflate(inflater, container,false)}
    ) {
        bind {
            binding.MainBlockBestProductItem.setOnClickListener {
                listenerClick.onRecyclerViewItemClick(binding.MainBlockBestProductItem, item)
            }
            binding.ImageBtnFavorite.setOnClickListener {
                listenerClick.onRecyclerViewItemClick(binding.ImageBtnFavorite, item)
            }


            binding.TVPriceDiscount.text = "$" + item.priceDiscount
            binding.TVPrice.text = "$" + item.price
            binding.TVNameProduct.text = item.title

            when (item.isFavorite) {
                true ->
                    binding.ImageBtnFavorite.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_favorite_fill,itemView.context.theme))
                false ->
                    binding.ImageBtnFavorite.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_favorite,itemView.context.theme))
            }

            binding.Image.load(item.imageUrl) {
                error(R.color.gray)
            }
        }
    }



    private fun bestSellerGridAdapter(listenerClick: RecyclerViewClickListener) = ListDelegationAdapter(
        gridBestSellerDelegate(listenerClick)
    )

}