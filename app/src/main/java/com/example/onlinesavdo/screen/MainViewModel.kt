package com.example.onlinesavdo.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinesavdo.model.CategoryModel
import com.example.onlinesavdo.model.OfferModel
import com.example.onlinesavdo.model.ProductModel
import com.example.onlinesavdo.api.repository.ShopRepository
import com.example.onlinesavdo.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    val repository= ShopRepository()
    val progress = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productData = MutableLiveData<List<ProductModel>>()

    fun getOffers(){
        repository.getOffers(progress, error, offersData)

    }
    fun getCategories(){
        repository.getCategories(error,categoriesData)
    }
    fun getTopProducts(){
        repository.getTopProducts(error,productData)
    }
    fun getProductsByCategory(id:Int){
        repository.getProductsByCategory(id,error,productData)
    }

    fun getProductsByIds(ids:List<Int>){
        repository.getProductsByIds(ids,error,progress,productData)
    }
    fun insertAllProducts2DB(items:List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getProductDao().insertAll(items)
            CoroutineScope(Dispatchers.Main).launch {
                error.value = "Malumotlar bazaga yuklandi!"
            }

        }
    }   fun insertAllCAtegories2DB(items: List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getCategoryDao().insertAll(items)
            CoroutineScope(Dispatchers.Main).launch {
                error.value = "Malumotlar bazaga yuklandi!"
            }

        }
    }
    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch {
            productData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getProductDao().getAllProducts()}
        }

    }fun getAllDBCategories(){
        CoroutineScope(Dispatchers.Main).launch {
            categoriesData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getCategoryDao().getAllCategories()}
        }

    }
}