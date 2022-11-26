package com.example.onlinesavdo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlinesavdo.model.CategoryModel
import com.example.onlinesavdo.model.ProductModel


@Database(entities = [CategoryModel::class, ProductModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun insitDatabase(context: Context) {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE= Room.databaseBuilder(context.applicationContext, AppDatabase::class.java ,"oline_shop").build()
                }
            }
        }
        fun getDatabase():AppDatabase{
            return INSTANCE!!
        }
    }
}