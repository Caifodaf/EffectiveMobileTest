package ru.android.effectivemobiletest.ui.product.adapter.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.LayoutShopPagerBinding
import ru.android.effectivemobiletest.models.ProductDetailsModel
import ru.android.effectivemobiletest.ui.product.ProductPageViewModel

@AndroidEntryPoint
class ShopViewPager2 : Fragment() {

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
    private var colorList: List<String> = listOf()
    private var capacityList: List<String> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeList()
    }

    private fun initUI() {
        val list = vm.dataDetailsPage.value!!

        vm.colorSelect = colorList[0]
        vm.capacitySelect = capacityList[0]

        binding.TVCPULabel.text = list.cpuName
        binding.TVCameraLabel.text = list.cameraType
        binding.TVRAMLabel.text = list.ramDefault
        binding.TVStorageLabel.text = list.storageDefault

        initColorPicker(list)
        initCapacityPicker(list.capacityList.size,list)
    }

    private fun observeList(){
        vm.dataDetailsPage.observe(viewLifecycleOwner, Observer { list ->
            colorList = list.colorList
            capacityList = list.capacityList

            initUI()
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun initCapacityPicker(size: Int, list: ProductDetailsModel) {
        if (size > 0){
            binding.TVStorageOne.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.orange_original))

            capacityPickerListenerOne()
            capacityPickerListenerTwo()
        }


    }

    @SuppressLint("ResourceAsColor")
    private fun capacityPickerListenerTwo() {
        binding.TVStorageOne.setOnClickListener{
            binding.TVStorageOne.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.orange_original))
            binding.TVStorageOne.setTextColor(ColorStateList.valueOf(Color.WHITE))

            binding.TVStorageTwo.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))
            binding.TVStorageTwo.setTextColor(ColorStateList.valueOf(R.color.gray))

            vm.capacitySelect = capacityList[0]
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun capacityPickerListenerOne() {
        binding.TVStorageTwo.setOnClickListener{
            binding.TVStorageTwo.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.orange_original))
            binding.TVStorageTwo.setTextColor(ColorStateList.valueOf(Color.WHITE))

            binding.TVStorageOne.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.white))
            binding.TVStorageOne.setTextColor(ColorStateList.valueOf(R.color.gray))

            vm.capacitySelect = capacityList[1]
        }
    }

    private fun initColorPicker( list: ProductDetailsModel) {
            binding.LLColorSelectOne.backgroundTintList = ColorStateList.valueOf(Color.parseColor(list.colorList[0]))
            binding.ImageSelectOne.visibility = View.VISIBLE
            colorPickerListenerOne()
            colorPickerListenerTwo()
    }

    private fun colorPickerListenerTwo() {
        binding.LLColorSelectOne.setOnClickListener{
            binding.ImageSelectOne.visibility = View.VISIBLE
            binding.ImageSelectTwo.visibility = View.INVISIBLE

            vm.colorSelect = colorList[0]
        }
    }

    private fun colorPickerListenerOne() {
        binding.LLColorSelectTwo.setOnClickListener{
            binding.ImageSelectTwo.visibility = View.VISIBLE
            binding.ImageSelectOne.visibility = View.INVISIBLE

            vm.colorSelect = colorList[1]
        }
    }

}