package ru.android.effectivemobiletest.ui.main.filtersheetpanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.FilterMainDialogBinding
import ru.android.effectivemobiletest.ui.main.MainViewModel


class FilterSheetPanel(val vm: MainViewModel) : BottomSheetDialogFragment() {

    private var _binding: FilterMainDialogBinding? = null
    private val binding get() = _binding!!

    private var isUpdated: Boolean = false

    private var oldBrandSelect: Int = vm.spinnerBrandSelect
    private var oldPriceSelect: Int = vm.spinnerPriceSelect
    private var oldSizeSelect: Int = vm.spinnerSizeSelect

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterMainDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var toast: Toast

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinnerUI()
    }

    private fun initSpinnerUI() {

        spinnerBrand()
        spinnerPrice()
        spinnerSize()

        buttonClose()
        buttonDone()
    }

    private fun buttonClose(){
        binding.ImageBtnClose.setOnClickListener{
            dismiss()
        }
    }

    private fun buttonDone(){
        binding.TVBtnDone.setOnClickListener{
            // Todo: Add navigate to more best seller
            toast = Toast.makeText(requireContext(), "Click done api post", Toast.LENGTH_SHORT)
            toast.show()

            dismiss()
        }
    }

    private fun spinnerBrand(){
        ArrayAdapter.createFromResource(requireContext(), R.array.brand_array, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.SpinnerBrand.adapter = adapter
            binding.SpinnerBrand.setSelection(vm.spinnerBrandSelect)
        }

        binding.SpinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.brand_array)
                binding.SpinnerBrand.setSelection(selectedItemPosition)
                vm.spinnerBrandSelect = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun spinnerPrice(){
        ArrayAdapter.createFromResource(requireContext(), R.array.price_array, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.SpinnerPrice.adapter = adapter
            binding.SpinnerPrice.setSelection(vm.spinnerPriceSelect)
        }

        binding.SpinnerPrice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.price_array)
                binding.SpinnerPrice.setSelection(selectedItemPosition)
                vm.spinnerPriceSelect = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun spinnerSize(){
        ArrayAdapter.createFromResource(requireContext(), R.array.size_array, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.SpinnerSize.adapter = adapter
            binding.SpinnerSize.setSelection(vm.spinnerSizeSelect)
        }

        binding.SpinnerSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.size_array)
                binding.SpinnerSize.setSelection(selectedItemPosition)
                vm.spinnerSizeSelect = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        checkUpdateSelected()
    }

    private fun checkUpdateSelected() {
        if(oldBrandSelect != vm.spinnerBrandSelect)
            isUpdated = true

        if(oldPriceSelect != vm.spinnerPriceSelect)
            isUpdated = true

        if(oldSizeSelect != vm.spinnerSizeSelect)
            isUpdated = true

        if (isUpdated)
            vm.updateFilterOption()
    }

}