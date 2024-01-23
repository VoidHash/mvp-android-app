package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.NewsModel
import com.voidhash.mvp_android_app.framework.network.NewsDataSource

class NewsPresenter(
    val view: NewsContract.View,
    private val dataSource: NewsDataSource
) : NewsContract.Presenter {

    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getBreakingNews(this)
    }

    override fun onSuccess(newsModel: NewsModel) {
        this.view.showArticleList(newsModel.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }
}