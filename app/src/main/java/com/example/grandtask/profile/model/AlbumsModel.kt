package com.example.grandtask.profile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumsModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val userId: Int
)
