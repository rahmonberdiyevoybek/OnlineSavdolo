package com.example.onlinesavdo.screen

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.onlinesavdo.R
import com.example.onlinesavdo.databinding.ActivityMainBinding
import com.example.onlinesavdo.screen.cart.CartFragment
import com.example.onlinesavdo.screen.changelanguage.ChangeLanguageFragment
import com.example.onlinesavdo.screen.favorite.FavoriteFragment
import com.example.onlinesavdo.screen.home.HomeFragment
import com.example.onlinesavdo.screen.profile.ProfileFragment
import com.example.onlinesavdo.utils.LocaleManager
import com.onesignal.OneSignal


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val ONESIGNAL_APP_ID = "47f6424b-6b08-4788-be30-5856071415c1"

    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        viewModel = MainViewModel()

        viewModel.productData.observe(this, Observer {
            viewModel.insertAllProducts2DB(it)
            homeFragment.loadData()
        })
        viewModel.categoriesData.observe(this, Observer {
            viewModel.insertAllCAtegories2DB(it)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(this,it, Toast.LENGTH_LONG).show()
        })


        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

         binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.actionHome){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                activeFragment = homeFragment
            }else if (it.itemId == R.id.actionFavorite){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            }else if (it.itemId == R.id.actionCart){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment).commit()
                activeFragment = cartFragment
            }else if (it.itemId == R.id.actionProfile){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                activeFragment = profileFragment
            }

            return@setOnNavigationItemSelectedListener true

        }
        binding.btnMenu.setOnClickListener {
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager,fragment.tag)
        }
        loadData()

    }

    fun loadData(){
        viewModel.getTopProducts()
        viewModel.getCategories()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}