package com.dicoding.submission2.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.submission2.data.local.FavoriteUser
import com.dicoding.submission2.data.local.FavoriteUserDao
import com.dicoding.submission2.data.local.UserDatabase

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private var favoriteUserDao: FavoriteUserDao?
    private var userDB : UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(app)
        favoriteUserDao = userDB?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao?.getFavoriteUser()!!
    }
}