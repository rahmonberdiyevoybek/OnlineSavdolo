package com.example.onlinesavdo.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinesavdo.R
import com.example.onlinesavdo.databinding.FragmentFavoriteBinding
import com.example.onlinesavdo.screen.MainViewModel
import com.example.onlinesavdo.utils.PrefUtils
import com.example.onlinesavdo.view.ProductAdapter


class FavoriteFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.productData.observe(this, Observer {

            binding.recyclerProducts2.adapter = ProductAdapter(it)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(),it,Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            binding.swipe.isRefreshing  =it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerProducts2.layoutManager = LinearLayoutManager(requireActivity())
        binding.swipe.setOnRefreshListener {
            loadData()
        }
loadData()
    }
    fun loadData(){

        viewModel.getProductsByIds(PrefUtils.getFavoriteList())
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}