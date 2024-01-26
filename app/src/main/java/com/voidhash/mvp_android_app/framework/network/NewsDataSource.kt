package com.voidhash.mvp_android_app.framework.network

import android.content.Context
import com.voidhash.mvp_android_app.framework.db.ArticleDatabase
import com.voidhash.mvp_android_app.framework.db.NewsRepository
import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.presenter.FavoriteContract
import com.voidhash.mvp_android_app.framework.presenter.NewsContract
import com.voidhash.mvp_android_app.framework.presenter.SearchContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class NewsDataSource(context: Context) {

    private var db: ArticleDatabase = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)

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

    fun saveArticle(articlesItem: ArticlesItem) {
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(articlesItem)
        }
    }

    fun getAllArticle(callback: NewsContract.View) {
        var allArticles: List<ArticlesItem>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll()

            withContext(Dispatchers.Main) {
                callback.showArticleList(allArticles)
            }
        }
    }

    fun deleteArticle(articlesItem: ArticlesItem?) {
        GlobalScope.launch(Dispatchers.Main) {
            articlesItem?.let { articlesItem ->
                newsRepository.delete(articlesItem)
            }
        }
    }

}