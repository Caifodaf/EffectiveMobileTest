package ru.android.effectivemobiletest.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.android.effectivemobiletest.api.Repository
import ru.android.effectivemobiletest.api.model.ErrorResponse
import ru.android.effectivemobiletest.api.model.MainModelResponse
import ru.android.effectivemobiletest.localdata.DataCategory
import ru.android.effectivemobiletest.models.BestSellerModel
import ru.android.effectivemobiletest.models.CategoryModel
import ru.android.effectivemobiletest.models.HotSalesModel
import ru.android.effectivemobiletest.ui.main.adapters.category.CategoryAdapter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var job: Job

    var spinnerBrandSelect: Int = 0
    var spinnerPriceSelect: Int = 0
    var spinnerSizeSelect: Int = 0

    private val _list = MutableLiveData<MainModelResponse>()
    val list: MutableLiveData<MainModelResponse>
        get() = _list

    private val _listHotSales = MutableLiveData<List<HotSalesModel>>()
    val listHotSales: LiveData<List<HotSalesModel>>
        get() = _listHotSales

    private val _listBestSeller = MutableLiveData<List<BestSellerModel>>()
    val listBestSeller: LiveData<List<BestSellerModel>>
        get() = _listBestSeller

    private val _error = MutableLiveData<ErrorResponse>()
    val error: MutableLiveData<ErrorResponse>
        get() = _error

    init {
        loadDataApi()
    }

    private fun loadDataApi(){
        job = viewModelScope.launch(Dispatchers.IO) {
            repository.getMainPage()
                .catch { exception ->
                    Log.e("ErrorApi", exception.message.toString())
                    _error.postValue(ErrorResponse(0, "getMainPage catch - $exception", 0))
                }
                .collect {
                    when (it.code()) {
                        200 -> {
                            _list.postValue(it.body()!!)
                        }
                        401 -> _error.postValue(ErrorResponse(401, it.message().toString(), it.code()))
                        else -> _error.postValue(ErrorResponse(1, it.message().toString(), it.code()))
                    }
                }
        }
    }

    fun loadCategoryAdapter(adapter: CategoryAdapter) {
        val list = mutableListOf<CategoryModel>()

        DataCategory().setDataCategory(list)
        adapter.setMain(list)
    }

    fun loadHotSalesAdapter(adapter: CategoryAdapter, list: List<HotSalesModel>) {

        //adapter.setMain(list)
    }

    fun loadBestSalesAdapter(adapter: CategoryAdapter, list: List<BestSellerModel>) {

        //adapter.setMain(list)
    }

    fun updateFilterOption() {
        // TODO("Add update data to filter")
    }

    override fun onCleared() {
        if(::job.isInitialized) job.cancel()
        super.onCleared()
    }



}