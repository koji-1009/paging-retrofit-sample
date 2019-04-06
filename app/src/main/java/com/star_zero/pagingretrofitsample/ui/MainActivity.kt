package com.star_zero.pagingretrofitsample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
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
