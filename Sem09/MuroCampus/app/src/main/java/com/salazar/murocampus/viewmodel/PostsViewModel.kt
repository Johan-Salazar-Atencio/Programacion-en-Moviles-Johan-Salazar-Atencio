package com.salazar.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salazar.murocampus.data.PostRepository
import com.salazar.murocampus.data.mensajeDe
import com.salazar.murocampus.model.Comentario
import com.salazar.murocampus.model.NuevoPost
import com.salazar.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista: StateFlow<UiState<List<Post>>> = _lista.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val listaVisible: StateFlow<UiState<List<Post>>> =
        combine(_lista, _busqueda) { estado, texto ->
            if (estado is UiState.Exito && texto.isNotBlank()) {
                UiState.Exito(estado.datos.filter { it.title.contains(texto.trim(), ignoreCase = true) })
            } else {
                estado
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UiState.Cargando)

    init {
        cargar()
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    private val _publicado = MutableStateFlow<UiState<Post>?>(null)
    val publicado: StateFlow<UiState<Post>?> = _publicado.asStateFlow()

    fun publicar(titulo: String, texto: String) {
        viewModelScope.launch {
            _publicado.value = UiState.Cargando
            repo.crear(NuevoPost(userId = 1, title = titulo, body = texto))
                .onSuccess { _publicado.value = UiState.Exito(it) }
                .onFailure { _publicado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    private val _edicion = MutableStateFlow<UiState<Post>?>(null)
    val edicion: StateFlow<UiState<Post>?> = _edicion.asStateFlow()

    fun editarTitulo(id: Int, titulo: String) {
        viewModelScope.launch {
            _edicion.value = UiState.Cargando
            repo.editarTitulo(id, titulo)
                .onSuccess { editado ->
                    (_detalle.value as? UiState.Exito)?.let { actual ->
                        _detalle.value = UiState.Exito(actual.datos.copy(post = actual.datos.post.copy(title = editado.title)))
                    }
                    (_lista.value as? UiState.Exito)?.let { actual ->
                        _lista.value = UiState.Exito(
                            actual.datos.map { if (it.id == id) it.copy(title = editado.title) else it }
                        )
                    }
                    _edicion.value = UiState.Exito(editado)
                }
                .onFailure { _edicion.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarEdicion() {
        _edicion.value = null
    }

    private val _borrado = MutableStateFlow<UiState<Unit>?>(null)
    val borrado: StateFlow<UiState<Unit>?> = _borrado.asStateFlow()

    fun borrar(id: Int) {
        viewModelScope.launch {
            _borrado.value = UiState.Cargando
            repo.borrar(id)
                .onSuccess {
                    (_lista.value as? UiState.Exito)?.let { actual ->
                        _lista.value = UiState.Exito(actual.datos.filterNot { it.id == id })
                    }
                    _borrado.value = UiState.Exito(Unit)
                }
                .onFailure { _borrado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarBorrado() {
        _borrado.value = null
    }

    fun limpiarPublicado() {
        _publicado.value = null
    }

    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            val userId = _usuarioFiltro.value
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun abrir(id: Int) {
        viewModelScope.launch {
            _detalle.value = UiState.Cargando
            val (resPost, resComentarios) = coroutineScope {
                val post = async { repo.detalle(id) }
                val comentarios = async { repo.comentarios(id) }
                post.await() to comentarios.await()
            }
            val post = resPost.getOrElse {
                _detalle.value = UiState.Error(mensajeDe(it))
                return@launch
            }
            val comentarios = resComentarios.getOrElse {
                _detalle.value = UiState.Error(mensajeDe(it))
                return@launch
            }
            _detalle.value = UiState.Exito(DetalleUi(post, comentarios))
        }
    }
}
