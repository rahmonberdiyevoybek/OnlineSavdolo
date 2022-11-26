package com.example.onlinesavdo.api.repository

import androidx.lifecycle.MutableLiveData

import com.example.onlinesavdo.api.NetworkManager
import com.example.onlinesavdo.model.BaseResponse
import com.example.onlinesavdo.model.CategoryModel
import com.example.onlinesavdo.model.OfferModel
import com.example.onlinesavdo.model.ProductModel
import com.example.onlinesavdo.model.request.GetProductsByIdsRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  ShopRepository {
    val compositeDisposable = CompositeDisposable()
    fun getOffers(
        progress: MutableLiveData<Boolean>,
        errorData: MutableLiveData<String>,
        offersData: MutableLiveData<List<OfferModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<OfferModel>>>() {
                    override fun onNext(t: BaseResponse<List<OfferModel>>) {
                        if (t.success) {
                            progress.value = false
                            offersData.value = t.data

                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        errorData.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })

        )
    }

    fun getCategories(
        errorData: MutableLiveData<String>,
        categoryData: MutableLiveData<List<CategoryModel>>
    ) {
        compositeDisposable.add(
            NetworkManager.getApiService().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<BaseResponse<List<CategoryModel>>>() {
                    override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                        if (t.success) {
                            categoryData.value = t.data
                        }
                    }

                    override fun onError(e: Throwable) {
                        errorData.value = e.localizedMessage
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    fun getTopProducts(
        errorData: MutableLiveData<String>,
        productData: MutableLiveData<List<ProductModel>>
    ) {
        compositeDisposable.add(NetworkManager.getApiService().getTopProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                override fun onNext(t: BaseResponse<List<ProductModel>>) {
                    if (t.success) {
                        productData.value = t.data
                    } else {
                        errorData.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    errorData.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )
    }
    fun getProductsByCategory(id:Int,
        errorData: MutableLiveData<String>,
        productData: MutableLiveData<List<ProductModel>>
    ) {
        compositeDisposable.add(NetworkManager.getApiService().getCategoryProducts(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                override fun onNext(t: BaseResponse<List<ProductModel>>) {
                    if (t.success) {
                        productData.value = t.data
                    } else {
                        errorData.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    errorData.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )
    }

    fun getProductsByIds(ids:List<Int>, error: MutableLiveData<String>,progress: MutableLiveData<Boolean>, productData: MutableLiveData<List<ProductModel>>
    ) {
        progress.value =true
        compositeDisposable.add(NetworkManager.getApiService().getProductsByIds(GetProductsByIdsRequest(ids))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                override fun onNext(t: BaseResponse<List<ProductModel>>) {
                    progress.value = false
                    if (t.success) {
                        productData.value = t.data
                    } else {
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    progress.value = false
                    error.value = e.localizedMessage
                }

                override fun onComplete() {
                }
            })
        )
    }
}
