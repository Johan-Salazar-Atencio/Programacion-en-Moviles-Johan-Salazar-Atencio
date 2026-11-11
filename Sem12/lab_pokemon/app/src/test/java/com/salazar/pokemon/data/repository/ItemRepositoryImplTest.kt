package com.salazar.pokemon.data.repository

import com.salazar.pokemon.data.auth.UsuarioActual
import com.salazar.pokemon.data.remote.ApiService
import com.salazar.pokemon.data.remote.dto.DetalleDto
import com.salazar.pokemon.data.remote.dto.EntradaPokemonDto
import com.salazar.pokemon.data.remote.dto.ItemDto
import com.salazar.pokemon.data.remote.dto.TipoRespuestaDto
import com.salazar.pokemon.fakes.FakeItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ItemRepositoryImplTest {

    private val api = object : ApiService {
        override suspend fun listar() = TipoRespuestaDto(
            listOf("charmander" to 4, "charizard" to 6, "vulpix" to 37).map { (nombre, id) ->
                EntradaPokemonDto(ItemDto(nombre, "https://pokeapi.co/api/v2/pokemon/$id/"))
            }
        )

        override suspend fun detalle(id: Int): DetalleDto = error("no se usa")
    }

    private val usuario = UsuarioActual()
    private lateinit var repo: ItemRepositoryImpl

    @Before
    fun preparar() = runBlocking {
        repo = ItemRepositoryImpl(api, FakeItemDao(), usuario)
        usuario.establecer("admin")
        repo.refrescar()
        Unit
    }

    private suspend fun marcados() = repo.items.first().filter { it.esFavorito }.map { it.titulo }

    @Test
    fun cadaCuentaTieneSusPropiosFavoritos() = runBlocking {
        repo.alternarFavorito(6)

        usuario.establecer("usuario")
        repo.alternarFavorito(37)

        assertEquals(listOf("vulpix"), marcados())
        usuario.establecer("admin")
        assertEquals(listOf("charizard"), marcados())
    }

    @Test
    fun alternarDosVecesQuitaElFavorito() = runBlocking {
        repo.alternarFavorito(4)
        repo.alternarFavorito(4)

        assertTrue(marcados().isEmpty())
    }

    @Test
    fun sincronizarConservaLosFavoritos() = runBlocking {
        repo.alternarFavorito(6)

        repo.refrescar()

        assertEquals(listOf("charizard"), marcados())
    }

    @Test
    fun limpiarLaCacheNoBorraLosFavoritosDeLaCuenta() = runBlocking {
        repo.alternarFavorito(6)

        repo.limpiarCache()
        assertEquals(0, repo.cantidadGuardada())
        assertEquals(0, repo.cantidadFavoritos())

        repo.refrescar()
        assertEquals(listOf("charizard"), marcados())
        assertEquals(1, repo.cantidadFavoritos())
    }

    @Test
    fun laListaDeFavoritosSoloMuestraLosDeLaCuentaActual() = runBlocking {
        repo.alternarFavorito(4)
        usuario.establecer("usuario")

        assertTrue(repo.favoritos.first().isEmpty())
        assertFalse(marcados().contains("charmander"))
    }

    @Test
    fun sinSesionNoSeGuardanFavoritos() = runBlocking {
        usuario.establecer(null)

        repo.alternarFavorito(4)

        usuario.establecer("admin")
        assertTrue(marcados().isEmpty())
    }
}
