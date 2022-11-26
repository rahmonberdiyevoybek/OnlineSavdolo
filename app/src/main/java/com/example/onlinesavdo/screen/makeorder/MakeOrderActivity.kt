package com.example.onlinesavdo.screen.makeorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinesavdo.MapsActivity
import com.example.onlinesavdo.R
import com.example.onlinesavdo.databinding.ActivityMakeOrderBinding
import com.example.onlinesavdo.model.AddressModel
import com.example.onlinesavdo.model.ProductModel
import com.example.onlinesavdo.utils.Constants
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {
    var address: AddressModel? = null
    lateinit var binding: ActivityMakeOrderBinding
    lateinit var items: List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        items =intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }


  binding.tvTotalAmount.setText(items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ","").toDoubleOrNull()?:0.0)}.toString())
        binding.edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
        binding.cardViewBack.setOnClickListener {
            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(address: AddressModel) {
        this.address = address

        binding.edAddress.setText("${address.latitude},${address.longitude}")
    }
}