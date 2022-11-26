package com.example.onlinesavdo.api

import com.example.onlinesavdo.model.BaseResponse
import com.example.onlinesavdo.model.CategoryModel
import com.example.onlinesavdo.model.OfferModel
import com.example.onlinesavdo.model.ProductModel
import com.example.onlinesavdo.model.request.GetProductsByIdsRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Observable

interface Api {
    @GET("get_offers")
    fun getOffers():  io.reactivex.Observable<BaseResponse<List<OfferModel>>>

    @GET("get_categories")
    fun getCategories(): io.reactivex.Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): io.reactivex.Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id")categoryId: Int): io.reactivex.Observable<BaseResponse<List<ProductModel>>>


    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: GetProductsByIdsRequest): io.reactivex.Observable<BaseResponse<List<ProductModel>>>
}