package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.ArticlesItem

interface FavoriteContract {

    interface Presenter {
        fun onSuccess(articles: List<ArticlesItem>)
    }
}