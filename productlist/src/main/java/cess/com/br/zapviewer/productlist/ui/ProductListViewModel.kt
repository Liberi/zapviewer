package cess.com.br.zapviewer.productlist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cess.com.br.zapviewer.common.model.Product
import cess.com.br.zapviewer.productlist.repository.ProductListRepository
import io.reactivex.disposables.CompositeDisposable

class ProductListViewModel(private val repository: ProductListRepository): ViewModel() {
    private val disposables = CompositeDisposable()

    val loading = MutableLiveData<Boolean>()
    val result = MutableLiveData<List<Product>>()
    val error = MutableLiveData<String>()

    fun getProductList(portalFlavor: String) {
        disposables.add(
            repository.getProductList(portalFlavor)
                .doOnSubscribe {
                    loading.postValue(true)
                }
                .doOnError {
                    loading.postValue(false)
                    it.printStackTrace()
                }
                .doOnSuccess {
                    loading.postValue(false)
                }
                .subscribe(
                    {
                        result.postValue(it)
                    },
                    {
                        error.postValue(it.message)
                    }
                ))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}