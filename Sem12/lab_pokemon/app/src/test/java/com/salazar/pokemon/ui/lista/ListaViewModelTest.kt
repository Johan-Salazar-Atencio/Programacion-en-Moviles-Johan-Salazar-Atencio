package com.salazar.pokemon.ui.lista

import com.salazar.pokemon.domain.model.Item
import com.salazar.pokemon.fakes.FakeItemRepository
import com.salazar.pokemon.fakes.FakeNotificador
import com.salazar.pokemon.fakes.MainDispatcherRule
import com.salazar.pokemon.ui.UiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class ListaViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val pokemon = listOf(
        Item(4, "charmander", "#4", null),
        Item(6, "charizard", "#6", null),
        Item(37, "vulpix", "#37", null)
    )

    @Test
    fun alAbrirConDatosMuestraLaListaYNotifica() {
        val notificador = FakeNotificador()

        val vm = ListaViewModel(FakeItemRepository(pokemon), notificador)

        assertEquals(UiState.Exito(pokemon), vm.estado.value)
        assertEquals(listOf("Datos actualizados" to "Se guardaron 3 elementos"), notificador.mensajes)
    }

    @Test
    fun buscarFiltraPorNombreSinImportarMayusculas() {
        val vm = ListaViewModel(FakeItemRepository(pokemon), FakeNotificador())

        vm.buscar("CHAR")

        val datos = (vm.estado.value as UiState.Exito).datos
        assertEquals(listOf("charmander", "charizard"), datos.map { it.titulo })
    }

    @Test
    fun buscarSinCoincidenciasDevuelveListaVacia() {
        val vm = ListaViewModel(FakeItemRepository(pokemon), FakeNotificador())

        vm.buscar("zzz")

        assertEquals(UiState.Exito(emptyList<Item>()), vm.estado.value)
    }

    @Test
    fun alBorrarLaBusquedaVuelveLaListaCompleta() {
        val vm = ListaViewModel(FakeItemRepository(pokemon), FakeNotificador())

        vm.buscar("vul")
        vm.buscar("")

        assertEquals(UiState.Exito(pokemon), vm.estado.value)
    }

    @Test
    fun siFallaLaDescargaSinDatosMuestraError() {
        val repo = FakeItemRepository(emptyList(), Result.failure(IOException()))

        val vm = ListaViewModel(repo, FakeNotificador())

        assertEquals(UiState.Error("No hay conexión a internet"), vm.estado.value)
    }

    @Test
    fun siFallaLaDescargaConDatosMuestraAvisoYConservaLaLista() {
        val repo = FakeItemRepository(pokemon, Result.failure(IOException()))

        val vm = ListaViewModel(repo, FakeNotificador())

        assertEquals(UiState.Exito(pokemon), vm.estado.value)
        assertTrue(vm.aviso.value!!.startsWith("Sin conexión"))
    }
}
