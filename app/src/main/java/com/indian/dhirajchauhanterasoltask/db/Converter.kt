package com.indian.dhirajchauhanterasoltask.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.indian.dhirajchauhanterasoltask.model.Info


class Converter {

    @TypeConverter
    fun fromInfoToString(info: Info): String{
        return Gson().toJson(info)
    }

    @TypeConverter
    fun fromStringToInfo(info:String): Info{
        return Gson().fromJson(info, Info::class.java)
    }
}