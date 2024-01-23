package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.NewsModel
import com.voidhash.mvp_android_app.framework.network.NewsDataSource

class SearchPresenter(
    val view: SearchContract.View,
    private val dataSource: NewsDataSource
): SearchContract.Presenter {

    override fun search(term: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(newsModel: NewsModel) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }
}