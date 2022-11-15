package ru.android.effectivemobiletest.ui.cart.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.BestSellerProductItemBinding
import ru.android.effectivemobiletest.databinding.CartProductItemBinding
import ru.android.effectivemobiletest.databinding.CategoryItemBinding
import ru.android.effectivemobiletest.models.CartItemModel
import ru.android.effectivemobiletest.models.CategoryModel
import ru.android.effectivemobiletest.utilits.RecyclerViewClickListener

class CartAdapter(
    private val listenerClick: RecyclerViewClickListener
    ): RecyclerView.Adapter<CartAdapter.ViewHolder>()
{
    private val itemViewModels = mutableListOf<CartItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartProductItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.CCMainBlock.setOnClickListener {
            listenerClick.onRecyclerViewItemClick(holder.binding.CCMainBlock,
                itemViewModels[position])
        }
        holder.bind(itemViewModels)
    }

    fun setMain(list: List<CartItemModel>) {
        itemViewModels.clear()
        itemViewModels.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemViewModels.size

    inner class ViewHolder (val binding: CartProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForColorStateLists")
        fun bind(itemViewModels: MutableList<CartItemModel>) {
            Log.d("ListT", "Load in bind CartAdapter - $itemViewModels")
            val model = itemViewModels[absoluteAdapterPosition]

            binding.TvTitle.text = model.title
            binding.TvPrice.text = "$" + model.priceDiscount.toString()
            binding.TvCount // not api
            binding.Image.load(model.imageUrl){
                error(R.color.gray)
                transformations(RoundedCornersTransformation(20f))
            }

        }
    }
}