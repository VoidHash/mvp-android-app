package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.NewsModel

interface BaseContract {

    interface View {
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
    }

    interface Presenter {
        fun onSuccess(newsModel: NewsModel)
        fun onError(message: String)
        fun onComplete()
    }
}