package com.star_zero.pagingretrofitsample.paging

import androidx.paging.DataSource
import com.star_zero.pagingretrofitsample.api.GitHubAPI
import com.star_zero.pagingretrofitsample.data.Repo
import com.xwray.groupie.Item

class RepoDataSourceFactory<T : Item<*>>(
    api: GitHubAPI,
    converter: (Repo) -> T
) : DataSource.Factory<Int, T>() {

    val source = PageKeyedRepoDataSource(api, converter)

    override fun create(): DataSource<Int, T> {
        return source
    }
}
