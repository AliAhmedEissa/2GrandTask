package com.example.grandtask.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.grandtask.profile.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun addUsers(userModelDB: UserModel)

    @Query("select * from usermodel")
    suspend fun getAllUsers(): List<UserModel>
}