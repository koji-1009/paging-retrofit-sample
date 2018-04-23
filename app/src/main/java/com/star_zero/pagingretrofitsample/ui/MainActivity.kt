package com.star_zero.pagingretrofitsample.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import com.chibatching.pagedlistgroup.PagedListGroup
import com.star_zero.pagingretrofitsample.R
import com.star_zero.pagingretrofitsample.databinding.ActivityMainBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private val pagedListGroup = PagedListGroup<RepoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.recycler.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = GroupAdapter<ViewHolder>().apply {
                add(pagedListGroup)
            }
        }

        viewModel.repos.observe(this, Observer { pagedList ->
            Log.d(TAG, "Receive Result")
            pagedListGroup.submitList(pagedList)
        })

        viewModel.networkState.observe(this, Observer { networkState ->
            Log.d(TAG, "NetworkState: $networkState")
        })
    }
}
