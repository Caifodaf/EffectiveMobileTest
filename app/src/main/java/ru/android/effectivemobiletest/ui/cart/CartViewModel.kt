package ru.android.effectivemobiletest.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.android.effectivemobiletest.api.Repository
import ru.android.effectivemobiletest.api.model.ErrorResponse
import ru.android.effectivemobiletest.localdata.DataCategory
import ru.android.effectivemobiletest.models.*
import ru.android.effectivemobiletest.ui.cart.adapter.CartAdapter
import ru.android.effectivemobiletest.ui.main.adapters.category.CategoryAdapter
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private lateinit var job: Job


    private val _pageData = MutableLiveData<CartModel>()
    val pageData: LiveData<CartModel>
        get() = _pageData

    private val _listCart = MutableLiveData<List<CartItemModel>>()
    val listCart: LiveData<List<CartItemModel>>
        get() = _listCart

    private val _error = MutableLiveData<ErrorResponse>()
    val error: MutableLiveData<ErrorResponse>
        get() = _error

    init {
        loadDataApi()
    }

    private fun loadDataApi(){
        job = viewModelScope.launch(Dispatchers.IO) {
            repository.geCartPage()
                .catch { exception ->
                    Log.e("ErrorApi", exception.message.toString())
                    _error.postValue(ErrorResponse(0, "geCartPage catch - $exception", 0))
                }
                .collect {
                    when (it.code()) {
                        200 -> {
                            _pageData.postValue(it.body()!!)
                            _listCart.postValue(it.body()!!.basket)
                        }
                        401 -> _error.postValue(ErrorResponse(401, it.message().toString(), it.code()))
                        else -> _error.postValue(ErrorResponse(1, it.message().toString(), it.code()))
                    }
                }
        }
    }

    fun loadAdapter(adapter: CartAdapter, list: List<CartItemModel>) {
        adapter.setMain(list)
    }

    override fun onCleared() {
        if(::job.isInitialized) job.cancel()
        super.onCleared()
    }
}