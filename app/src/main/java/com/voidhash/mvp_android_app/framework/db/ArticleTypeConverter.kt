package com.voidhash.mvp_android_app.framework.db

import androidx.room.TypeConverter
import com.voidhash.mvp_android_app.framework.model.Source

class ArticleTypeConverter {

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}