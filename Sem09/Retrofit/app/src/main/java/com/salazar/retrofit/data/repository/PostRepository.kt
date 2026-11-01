package com.salazar.retrofit.data.repository

import com.salazar.retrofit.data.model.PostModel
import com.salazar.retrofit.data.remote.PostApiService

class PostRepository(
    private val apiService: PostApiService
) {
    suspend fun getPosts(): List<PostModel> = apiService.getPosts()

    suspend fun getPostById(id: Int): PostModel = apiService.getPostById(id)
}
