package ru.android.effectivemobiletest.ui.product

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
import ru.android.effectivemobiletest.localdata.DataCategory
import ru.android.effectivemobiletest.models.BestSellerModel
import ru.android.effectivemobiletest.models.CategoryModel
import ru.android.effectivemobiletest.models.HotSalesModel
import ru.android.effectivemobiletest.models.ProductDetailsModel
import ru.android.effectivemobiletest.ui.main.adapters.category.CategoryAdapter
import ru.android.effectivemobiletest.ui.product.adapter.ViewPagerFlowImagesAdapter
import javax.inject.Inject

@HiltViewModel
class ProductPageViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private lateinit var job: Job

    var colorSelect: String = ""
    var capacitySelect: String = ""

    private val _dataDetailsPage = MutableLiveData<ProductDetailsModel>()
    val dataDetailsPage: LiveData<ProductDetailsModel>
        get() = _dataDetailsPage

    private val _error = MutableLiveData<ErrorResponse>()
    val error: MutableLiveData<ErrorResponse>
        get() = _error

    init {
        loadDataApi()
    }

    private fun loadDataApi(){
        job = viewModelScope.launch(Dispatchers.IO) {
            repository.getDetailsProductPage()
                .catch { exception ->
                    Log.e("ErrorApi", exception.message.toString())
                    _error.postValue(ErrorResponse(0, "getMainPage catch - $exception", 0))
                }
                .collect {
                    when (it.code()) {
                        200 -> {
                            _dataDetailsPage.postValue(it.body()!!)
                        }
                        401 -> _error.postValue(ErrorResponse(401, it.message().toString(), it.code()))
                        else -> _error.postValue(ErrorResponse(1, it.message().toString(), it.code()))
                    }
                }
        }
    }

    fun loadImagesAdapter(adapter: ViewPagerFlowImagesAdapter, list: List<String>) {
        adapter.setMain(list)
    }


    override fun onCleared() {
        if(::job.isInitialized) job.cancel()
        super.onCleared()
    }

}