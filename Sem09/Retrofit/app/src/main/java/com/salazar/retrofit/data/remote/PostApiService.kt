package com.salazar.retrofit.data.remote

import com.salazar.retrofit.data.model.PhotoModel
import com.salazar.retrofit.data.model.PostModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostModel>

    @GET("photos")
    suspend fun getPhotos(@Query("_limit") limit: Int = 30): List<PhotoModel>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostModel
}
