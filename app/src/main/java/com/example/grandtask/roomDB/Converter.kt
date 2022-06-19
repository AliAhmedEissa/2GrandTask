package com.example.grandtask.roomDB

import androidx.room.TypeConverter
import com.example.grandtask.profile.model.Address
import com.example.grandtask.profile.model.AlbumsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.xml.transform.Source

class Converter {

    @TypeConverter
    fun fromListToString(albumsItem: List<AlbumsModel>): String? {
        val gson = Gson()
        return gson.toJson(albumsItem)
    }

    @TypeConverter
    fun fromStringToList(albumsItem: String?): List<AlbumsModel> {
        val type: Type = object : TypeToken<AlbumsModel>() {}.type
        return Gson().fromJson(albumsItem, type)
    }

    @TypeConverter
    fun fromSource(address: Address): String? {
        val gson = Gson()
        return gson.toJson(address)
    }

    @TypeConverter
    fun toSource(address: String?): Address {
        val type: Type = object : TypeToken<Address>() {}.type
        return Gson().fromJson(address, type)
    }
}