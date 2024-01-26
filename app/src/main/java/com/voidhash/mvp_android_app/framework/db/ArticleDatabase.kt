package com.voidhash.mvp_android_app.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.voidhash.mvp_android_app.framework.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1, exportSchema = false)
@TypeConverters(ArticleTypeConverter::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var instance: ArticleDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock) {
            instance ?: createDatabase(context).also { articleDatabase ->
                instance = articleDatabase
            }
        }

        private fun createDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
        }
    }
}