package com.salazar.retrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
