package com.example.grandtask.network

import com.example.grandtask.profile.model.AlbumsModel
import com.example.grandtask.profile.model.PhotosModel
import com.example.grandtask.profile.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/users")
    suspend fun getUsers(
    ): Response<ArrayList<UserModel>>

    @GET("/albums")
    suspend fun getUserAlbums(
        @Query("userId") userId: String,
    ): Response<ArrayList<AlbumsModel>>

    @GET("/photos")
    suspend fun getPhotos(
        @Query("albumId") albumId: String,
    ): Response<ArrayList<PhotosModel>>

}
