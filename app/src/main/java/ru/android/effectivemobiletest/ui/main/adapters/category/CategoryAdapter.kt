package ru.android.effectivemobiletest.ui.main.adapters.category

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.CategoryItemBinding
import ru.android.effectivemobiletest.models.CategoryModel
import ru.android.effectivemobiletest.utilits.RecyclerViewClickListener

class CategoryAdapter(
    private val listenerClick: RecyclerViewClickListener
    ): RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{
    private var selectedItemPos = 0
    private var lastItemSelectedPos = 0

    private val itemViewModels = mutableListOf<CategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == selectedItemPos)
            holder.selectedBg()
        else
            holder.defaultBg()

        holder.binding.CCCategoryMain.setOnClickListener {
            selectedItemPos = position

            notifyItemChanged(lastItemSelectedPos)
            lastItemSelectedPos = selectedItemPos

            notifyItemChanged(selectedItemPos)

            // Default OnClickListener
            listenerClick.onRecyclerViewItemClick(holder.binding.CCCategoryMain,
                itemViewModels[position])
        }

        holder.bind(itemViewModels)
    }

    fun setMain(list: List<CategoryModel>) {
        itemViewModels.clear()
        itemViewModels.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemViewModels.size

    inner class ViewHolder (val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForColorStateLists")
        fun bind(itemViewModels: MutableList<CategoryModel>) {
            Log.d("ListT", "Load in bind CategoryViewHolder - $itemViewModels")
            val model = itemViewModels[absoluteAdapterPosition]

            binding.TVTitle.text = model.title
            binding.ImageItem.load(model.image)

            //if(model.select){
            //    binding.TVTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_original))
            //    binding.ImageItem.imageTintList = (itemView.context).resources.getColorStateList(R.color.white)
            //    binding.ContainerImageItem.backgroundTintList = (itemView.context).resources.getColorStateList(R.color.orange_original)
            //}
        }

        fun defaultBg() {
            binding.TVTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_blue_original))
            binding.ImageItem.imageTintList = (itemView.context).resources.getColorStateList(R.color.gray)
            binding.ContainerImageItem.backgroundTintList = (itemView.context).resources.getColorStateList(R.color.white)
        }

        fun selectedBg() {
            binding.TVTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.orange_original))
            binding.ImageItem.imageTintList = (itemView.context).resources.getColorStateList(R.color.white)
            binding.ContainerImageItem.backgroundTintList = (itemView.context).resources.getColorStateList(R.color.orange_original)
        }
    }
}