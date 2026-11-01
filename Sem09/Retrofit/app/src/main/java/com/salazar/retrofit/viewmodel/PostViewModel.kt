package com.salazar.retrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.salazar.retrofit.data.local.PostEntity
import com.salazar.retrofit.data.model.PhotoModel
import com.salazar.retrofit.data.model.PostModel
import com.salazar.retrofit.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts = MutableStateFlow<List<PostModel>>(emptyList())
    val posts: StateFlow<List<PostModel>> = _posts

    private val _selectedPost = MutableStateFlow<PostModel?>(null)
    val selectedPost: StateFlow<PostModel?> = _selectedPost

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _favorites = MutableStateFlow<List<PostEntity>>(emptyList())
    val favorites: StateFlow<List<PostEntity>> = _favorites

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        viewModelScope.launch {
            repository.getFavorites().collect { _favorites.value = it }
        }
    }

    fun toggleFavorite(post: PostModel) {
        viewModelScope.launch {
            if (repository.isFavorite(post.id)) {
                repository.deleteFavorite(post.id)
            } else {
                repository.saveFavorite(post)
            }
            _isFavorite.value = repository.isFavorite(post.id)
        }
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch { repository.deleteFavorite(id) }
    }

    private val _photos = MutableStateFlow<List<PhotoModel>>(emptyList())
    val photos: StateFlow<List<PhotoModel>> = _photos

    fun loadPhotos() {
        viewModelScope.launch {
            try {
                _photos.value = repository.getPhotos()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "No se pudieron cargar las fotos"
            }
        }
    }

    fun loadPosts() {
        viewModelScope.launch {
            try {
                _posts.value = repository.getPosts()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "No se pudo cargar la lista"
            }
        }
    }

    fun loadPostById(id: Int) {
        viewModelScope.launch {
            _selectedPost.value = null
            try {
                _selectedPost.value = repository.getPostById(id)
                _isFavorite.value = repository.isFavorite(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "No se pudo cargar la publicacion"
            }
        }
    }

    class Factory(private val repository: PostRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PostViewModel(repository) as T
    }
}
