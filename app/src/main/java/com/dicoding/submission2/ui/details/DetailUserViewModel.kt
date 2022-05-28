package com.dicoding.submission2.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission2.api.RetrofitClient
import com.dicoding.submission2.data.DetailUserResponse
import com.dicoding.submission2.data.local.FavoriteUser
import com.dicoding.submission2.data.local.FavoriteUserDao
import com.dicoding.submission2.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(app: Application): AndroidViewModel(app) {
    val user = MutableLiveData<DetailUserResponse>()

    private var favoriteUserDao: FavoriteUserDao?
    private var userDB : UserDatabase? = UserDatabase.getDatabase(app)

    init {
        favoriteUserDao = userDB?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object: Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if(response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavUser(username: String, id: Int, avatar_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                id,
                username,
                avatar_url
            )
            favoriteUserDao?.addToFavorite(user)
        }
    }

    fun checkFavUser(id: Int) = favoriteUserDao?.checkUser(id)

    fun removeFavUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.deleteFavoriteUser(id)
        }
    }
}