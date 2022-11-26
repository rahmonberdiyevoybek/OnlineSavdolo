package com.example.onlinesavdo.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinesavdo.R
import com.example.onlinesavdo.databinding.ActivitySplash2Binding

class SplashActivity2 : AppCompatActivity() {
    lateinit var binding: ActivitySplash2Binding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySplash2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.animationView.postDelayed({
               finish()
            startActivity(Intent(this,MainActivity::class.java))

        },
            5000)
    }
}