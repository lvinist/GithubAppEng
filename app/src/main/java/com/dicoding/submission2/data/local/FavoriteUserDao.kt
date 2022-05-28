package com.dicoding.submission2.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    fun addToFavorite(fav_user: FavoriteUser)

    @Query("select * from fav_user")
    fun getFavoriteUser() : LiveData<List<FavoriteUser>>

    @Query("select count(*) from fav_user where fav_user.id = :id")
    fun checkUser(id: Int) : Int

    @Query("delete from fav_user where fav_user.id = :id")
    fun deleteFavoriteUser(id: Int) : Int
}