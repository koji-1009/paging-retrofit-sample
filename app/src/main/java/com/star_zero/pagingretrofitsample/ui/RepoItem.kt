package com.star_zero.pagingretrofitsample.ui

import com.star_zero.pagingretrofitsample.R
import com.star_zero.pagingretrofitsample.data.Repo
import com.star_zero.pagingretrofitsample.databinding.ItemRepoBinding
import com.xwray.groupie.databinding.BindableItem

class RepoItem(val repo: Repo) : BindableItem<ItemRepoBinding>() {

    override fun getLayout(): Int = R.layout.item_repo

    override fun getId(): Long = repo.id

    override fun bind(viewBinding: ItemRepoBinding, position: Int) {
        viewBinding.repo = repo
    }
}
