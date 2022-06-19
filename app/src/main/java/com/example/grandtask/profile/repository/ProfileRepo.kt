package com.example.grandtask.profile.repository

import com.example.grandtask.network.ApiService
import com.example.grandtask.profile.model.AlbumsModel
import com.example.grandtask.profile.model.UserModel
import com.example.grandtask.roomDB.dao.AlbumDao
import com.example.grandtask.roomDB.dao.UserDao
import javax.inject.Inject
import javax.inject.Singleton

class ProfileRepo @Inject constructor(
    @Singleton private val apiService: ApiService,
    @Singleton private val userDao: UserDao,
    @Singleton private val albumDao: AlbumDao
) {

    suspend fun getUsers() = apiService.getUsers()

    suspend fun getUserAlbums(userId :String) = apiService.getUserAlbums(userId)

    suspend fun getPhotos(albumId :String) = apiService.getPhotos(albumId)

    suspend fun addUsersInDB(userModel: UserModel) = userDao.addUsers(userModel)

    suspend fun getAllUsersFromDB() = userDao.getAllUsers()

    suspend fun addUserAlbumsInDB(albumsModelList: ArrayList<AlbumsModel>?) = albumDao.addUserAlbums(albumsModelList)

    suspend fun getUserAlbumsFromDB(userId: String) = albumDao.getAllUserAlbums(userId)
}