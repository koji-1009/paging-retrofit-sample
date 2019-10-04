package com.star_zero.pagingretrofitsample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.star_zero.pagingretrofitsample.api.GitHubAPI
import com.star_zero.pagingretrofitsample.data.NetworkState
import com.star_zero.pagingretrofitsample.data.Repo
import com.star_zero.pagingretrofitsample.paging.RepoDataSourceFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 50
    }

    val repos: LiveData<PagedList<Repo>>

    val networkState: LiveData<NetworkState>

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(GitHubAPI::class.java)

        val factory = RepoDataSourceFactory(api, ::convertToItem)
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()

        repos = LivePagedListBuilder(factory, config).build()
        networkState = factory.source.networkState
    }

    private fun convertToItem(repo: Repo): Repo = repo
}
