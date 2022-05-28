package com.dicoding.submission2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fav_user")
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String
) : Serializable
