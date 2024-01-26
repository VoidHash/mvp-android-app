package com.voidhash.mvp_android_app.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voidhash.mvp_android_app.framework.model.ArticlesItem

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(articleEntity: ArticlesItem) : Long

    @Query("SELECT * FROM articles")
    fun getAll(): List<ArticlesItem>

    @Delete
    suspend fun delete(articleEntity: ArticlesItem)
}