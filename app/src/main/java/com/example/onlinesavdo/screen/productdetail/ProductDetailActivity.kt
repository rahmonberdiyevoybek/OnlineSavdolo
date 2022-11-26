package com.example.onlinesavdo.screen.productdetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.onlinesavdo.R
import com.example.onlinesavdo.databinding.ActivityProductDetailBinding
import com.example.onlinesavdo.model.ProductModel
import com.example.onlinesavdo.utils.Constants
import com.example.onlinesavdo.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item : ProductModel
    lateinit var binding: ActivityProductDetailBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardViewBack.setOnClickListener {
            finish()
        }
        binding.cardViewFavorite.setOnClickListener {
            PrefUtils.setFavorutes(item)

            if (PrefUtils.checkFavorite(item)){
                binding.imgFavorite2.setImageResource(R.drawable.heart_svgrepo_com)

            }else{
                binding.imgFavorite2.setImageResource(R.drawable.favorite)
            }
        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        binding.tvProductName.text = item.name
        binding.tvTitle.text = item.name
        binding.tvProductPrice.text = item.price

        if (PrefUtils.getCartCount(item)>0){
            binding.btnAdd2cart.visibility = View.GONE
        }
        if (PrefUtils.checkFavorite(item)){
            binding.imgFavorite2.setImageResource(R.drawable.heart_svgrepo_com)

        }else{
            binding.imgFavorite2.setImageResource(R.drawable.favorite)
        }

        Glide.with(this).load(Constants.HOST_IMAGE +item.image ).into(binding.imgProduct)

        binding.btnAdd2cart.setOnClickListener {
            item .cartCount = 1
            PrefUtils.setCart(item)
            Toast.makeText(this,"Product added to cart",Toast.LENGTH_LONG).show()
            finish()

        }
    }
}