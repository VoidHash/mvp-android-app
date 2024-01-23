package com.voidhash.mvp_android_app.framework.network

import com.voidhash.mvp_android_app.framework.presenter.NewsContract
import com.voidhash.mvp_android_app.framework.presenter.SearchContract
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDataSource() {

    @OptIn(DelicateCoroutinesApi::class)
    fun getBreakingNews(callback: NewsContract.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitInstance.api.getBreakingNews("us")
                if(response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        callback.onSuccess(newsResponse)
                    }
                    callback.onComplete()
                } else {
                    callback.onError(response.message())
                    callback.onComplete()
                }
            }catch (e: Exception) {
                callback.onError(e.message.toString())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchContract.Presenter) {
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.api.searchNews(term)
                if(response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        callback.onSuccess(newsResponse)
                    }
                } else {
                    callback.onError(response.message())
                }
                callback.onComplete()
            } catch (e: Exception) {
                callback.onError(e.message.toString())
                callback.onComplete()
            }
        }
    }


}