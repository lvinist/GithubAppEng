package com.dicoding.submission2.data

data class DetailUserResponse(
    val login : String,
    val id : Int,
    val avatar_url : String,
    val followers_url : String,
    val following_url : String,
    val name : String,
    val followers : Int,
    val following : Int,
    val location : String,
    val company : String,
    val public_repos : Int
)