package com.voidhash.mvp_android_app.framework.presenter

import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.model.NewsModel

interface NewsContract {

    interface View: BaseContract.View {
        fun showArticleList(abstractList: List<ArticlesItem?>?)
    }

    interface Presenter: BaseContract.Presenter {
        fun requestAll()
    }
}