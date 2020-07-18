package com.platzi.android.rickandmorty.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListStringConverters {

    //region Fields

    private val gson: Gson = Gson()

    //endregion

    //region Public Methods

    @TypeConverter
    fun stringToStringList(data: String?): List<String?>? {
        if (data == null) {
            return Collections.emptyList()
        }

        return gson.fromJson(data, object : TypeToken<List<String?>?>() {}.type)
    }

    @TypeConverter
    fun stringListToString(someObjects: List<String?>?): String? {
        return gson.toJson(someObjects)
    }

    //endregion
}
