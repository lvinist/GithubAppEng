package com.dicoding.submission2.api

import com.dicoding.submission2.data.DetailUserResponse
import com.dicoding.submission2.data.User
import retrofit2.Call
import com.dicoding.submission2.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query : String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username : String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username : String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username : String
    ):Call<ArrayList<User>>


}