package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.network.NewsDataSource

class FavoritePresenter(val view: NewsContract.View, private val dataSource: NewsDataSource)
    : FavoriteContract.Presenter {

    fun getAll() {
        this.dataSource.getAllArticle(view)
    }

    fun saveArticle(article: ArticlesItem) {
        this.dataSource.saveArticle(article)
    }

    fun deleteArticle(article: ArticlesItem) {
        this.dataSource.deleteArticle(article)
    }

    override fun onSuccess(articles: List<ArticlesItem>) {
        this.view.showArticleList(articles.toMutableList())
    }
}