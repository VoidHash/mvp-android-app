package com.voidhash.mvp_android_app.framework.db

import com.voidhash.mvp_android_app.framework.model.ArticlesItem

class NewsRepository(private val db: ArticleDatabase) {

    suspend fun updateInsert(articlesItem: ArticlesItem) =
        db.getArticleDao().updateInsert(articlesItem)

    fun getAll(): List<ArticlesItem> = db.getArticleDao().getAll()

    suspend fun delete(articlesItem: ArticlesItem) = db.getArticleDao().delete(articlesItem)
}