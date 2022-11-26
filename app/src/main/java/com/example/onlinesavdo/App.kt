package com.example.onlinesavdo

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.onlinesavdo.db.AppDatabase
import com.orhanobut.hawk.Hawk

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabase.insitDatabase(this)
    }

}