package com.salazar.pokemon.ui.detalle

import com.salazar.pokemon.domain.model.Estadistica
import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.fakes.FakeItemRepository
import com.salazar.pokemon.fakes.MainDispatcherRule
import com.salazar.pokemon.ui.UiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetalleViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val charizard = Item(
        id = 6,
        titulo = "charizard",
        subtitulo = "#6",
        imagenUrl = null,
        estadisticas = listOf(Estadistica("HP", 78), Estadistica("Ataque", 84))
    )

    @Test
    fun alAbrirMuestraElDetalleConSusEstadisticas() {
        val vm = DetalleViewModel(FakeItemRepository(listOf(charizard)), 6)

        val item = (vm.estado.value as UiState.Exito).datos
        assertEquals(listOf("HP", "Ataque"), item.estadisticas.map { it.nombre })
    }

    @Test
    fun alternarFavoritoActualizaElEstadoYElRepositorio() = runBlocking {
        val repo = FakeItemRepository(listOf(charizard))
        val vm = DetalleViewModel(repo, 6)

        vm.alternarFavorito()

        assertTrue((vm.estado.value as UiState.Exito).datos.esFavorito)
        assertEquals(1, repo.cantidadFavoritos())

        vm.alternarFavorito()

        assertFalse((vm.estado.value as UiState.Exito).datos.esFavorito)
        assertEquals(0, repo.cantidadFavoritos())
    }

    @Test
    fun siNoExisteElPokemonMuestraError() {
        val vm = DetalleViewModel(FakeItemRepository(emptyList()), 99)

        assertTrue(vm.estado.value is UiState.Error)
    }
}
