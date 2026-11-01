package com.salazar.retrofit.data.repository

import com.salazar.retrofit.data.local.PostDao
import com.salazar.retrofit.data.local.PostEntity
import com.salazar.retrofit.data.model.PhotoModel
import com.salazar.retrofit.data.model.PostModel
import com.salazar.retrofit.data.remote.PostApiService
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val apiService: PostApiService,
    private val postDao: PostDao
) {
    suspend fun getPosts(): List<PostModel> = apiService.getPosts()

    suspend fun getPostById(id: Int): PostModel = apiService.getPostById(id)

    suspend fun getPhotos(): List<PhotoModel> = apiService.getPhotos()

    suspend fun saveFavorite(post: PostModel) {
        postDao.insertPost(
            PostEntity(
                id = post.id,
                userId = post.userId,
                title = post.title,
                body = post.body
            )
        )
    }

    fun getFavorites(): Flow<List<PostEntity>> = postDao.getFavoritePosts()

    suspend fun deleteFavorite(id: Int) = postDao.deletePost(id)

    suspend fun isFavorite(id: Int): Boolean = postDao.isFavorite(id)
}
