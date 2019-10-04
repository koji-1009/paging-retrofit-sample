package com.star_zero.pagingretrofitsample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.dr1009.app.PagedGroupAdapter
import com.star_zero.pagingretrofitsample.R
import com.star_zero.pagingretrofitsample.data.Repo
import com.star_zero.pagingretrofitsample.databinding.ActivityMainBinding
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.databinding.BindableItem

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val adapter = PagedGroupAdapter<Repo, GroupieViewHolder>(
            PagedGroupAdapter.ItemCreator<BindableItem<*>> {
                return@ItemCreator when (it) {
                    is Repo -> RepoItem(it)
                    else -> throw IllegalStateException("")
                }
            },
            object : DiffUtil.ItemCallback<Repo>() {
                override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                    oldItem == newItem
            }
        )
        binding.recycler.let {
            it.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            it.adapter = adapter
        }

        viewModel.repos.observe(this) {
            Log.d(TAG, "submit paged list: $it")
            adapter.submitPagedList(it)
        }
        viewModel.networkState.observe(this) { networkState ->
            Log.d(TAG, "NetworkState: $networkState")
        }
    }
}
