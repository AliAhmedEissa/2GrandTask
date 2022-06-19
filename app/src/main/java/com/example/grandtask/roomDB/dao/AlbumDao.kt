package com.example.grandtask.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.grandtask.profile.model.AlbumsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert
    suspend fun addUserAlbums(albumsModelList: List<AlbumsModel>?)

    @Query("select * from albumsModel where userId = :userId")
    suspend fun getAllUserAlbums(userId :String): List<AlbumsModel>
}