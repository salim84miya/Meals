package com.example.meals.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class DateTypeConvertor {

    @TypeConverter
    fun dateToString(attribute:Any?):String{
        if(attribute ==null){
            return " "
        }
        return attribute.toString()
    }

    @TypeConverter
    fun stringToDate(attribute: String?):Any{
        if (attribute == null){
            return  " "
        }
        return  " "
    }
}