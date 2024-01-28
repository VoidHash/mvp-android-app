package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.NewsModel
import com.voidhash.mvp_android_app.framework.network.NewsDataSource

class SearchPresenter(
    val view: SearchContract.View,
    private val dataSource: NewsDataSource
): SearchContract.Presenter {

    override fun search(term: String) {
        this.view.showProgressBar()
        this.dataSource.searchNews(term, this)
    }

    override fun onSuccess(newsModel: NewsModel) {
        newsModel.articles?.toList()?.let { this.view.showArticles(it) }
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }
}