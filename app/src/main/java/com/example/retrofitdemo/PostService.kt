package com.example.retrofitdemo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    suspend fun getPosts() :Response<Post>

    @GET("posts")
    suspend fun getSortedPosts(@Query("userId") userId:Int) :Response<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path(value = "id") postId: Int) :Response<PostItem>

}