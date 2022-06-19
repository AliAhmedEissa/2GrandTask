package com.example.grandtask.di

import android.content.Context
import androidx.room.Room
import com.example.grandtask.roomDB.LocalDataBase
import com.example.grandtask.roomDB.dao.AlbumDao
import com.example.grandtask.roomDB.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {



    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, LocalDataBase::class.java
        ,"2GrandTask")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(appDatabase: LocalDataBase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideAlbumDao(appDatabase: LocalDataBase): AlbumDao {
        return appDatabase.albumDao()
    }
}