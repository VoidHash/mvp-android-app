package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.model.NewsModel

interface SearchContract {

    interface View: BaseContract.View {
        fun showArticles(articles: List<ArticlesItem>)
    }

    interface Presenter: BaseContract.Presenter {
        fun search(term: String)
    }
}