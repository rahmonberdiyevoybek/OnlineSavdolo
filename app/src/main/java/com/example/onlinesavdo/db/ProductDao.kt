package com.example.onlinesavdo.db

import android.widget.RemoteViews.RemoteCollectionItems
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlinesavdo.model.CategoryModel
import com.example.onlinesavdo.model.ProductModel


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ProductModel>)


    @Query("select * from products")
    fun getAllProducts():List<ProductModel>
}