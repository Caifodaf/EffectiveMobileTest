package ru.android.effectivemobiletest.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.android.effectivemobiletest.R
import ru.android.effectivemobiletest.databinding.CartFragmentBinding
import ru.android.effectivemobiletest.models.CartModel
import ru.android.effectivemobiletest.ui.cart.adapter.CartAdapter
import ru.android.effectivemobiletest.utilits.RecyclerViewClickListener

@AndroidEntryPoint
class CartFragment : Fragment(), RecyclerViewClickListener {

    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var toast: Toast

    private val cartAdapter = CartAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        observeList()
    }

    private fun observeList() {
        vm.pageData.observe(viewLifecycleOwner, Observer { list ->
            Log.e("ErrorApi", list.toString())
            updateUI(list)
        })
        vm.listCart.observe(viewLifecycleOwner, Observer { list ->
            vm.loadAdapter(cartAdapter, list)
        })
        vm.error.observe(viewLifecycleOwner, Observer { error ->
            //todo
        })
    }

    private fun updateUI(list: CartModel) {
        binding.TVAllCost.text = "$" + list.total
        binding.TVDeliveryCost.text = list.deliveryCost
    }

    private fun initUI(){
        initRecyclerViewCategory()
        initButtons()
    }

    private fun initRecyclerViewCategory() {
        binding.ProductListRecyclerView.apply {
            adapter = cartAdapter
        }
    }


    private fun initButtons() {
        buttonChangeRegion()
        buttonBack()
        buttonCheckout()
    }

    private fun buttonChangeRegion(){
        binding.ImageBtnGeo.setOnClickListener{
            // Todo: Add fun change region
            toast = Toast.makeText(requireContext(), "Click change location", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun buttonBack(){
        binding.ImageBtnBack.setOnClickListener{
            parentFragmentManager.popBackStack();
        }
    }


    private fun buttonCheckout(){
        binding.TVBtnCheckout.setOnClickListener{
            // Todo: Add fun
            toast = Toast.makeText(requireContext(), "Click to Checkout", Toast.LENGTH_SHORT)
            toast.show()
        }
    }


    override fun onRecyclerViewItemClick(view: View, list: Any?) {
        when(view.id){
            R.id.ImageMinus ->{
                // Todo: Add item cart fun
                toast = Toast.makeText(requireContext(), "Click item minus api lost", Toast.LENGTH_SHORT)
                toast.show()
            }
            R.id.ImagePlus ->{
                // Todo: Add item cart fun
                toast = Toast.makeText(requireContext(), "Click item plus api lost", Toast.LENGTH_SHORT)
                toast.show()
            }
            R.id.ImageTrash ->{
                // Todo: Add item cart fun
                toast = Toast.makeText(requireContext(), "Click item delete api lost", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}