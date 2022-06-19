package com.example.grandtask.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.grandtask.profile.model.AlbumsModel
import com.example.grandtask.profile.model.UserModel
import com.example.grandtask.roomDB.dao.AlbumDao
import com.example.grandtask.roomDB.dao.UserDao

@Database(entities = [UserModel::class, AlbumsModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class LocalDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun albumDao(): AlbumDao
}