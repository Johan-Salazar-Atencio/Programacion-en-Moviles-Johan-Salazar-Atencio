package com.salazar.pokemon.ui.login

import com.salazar.pokemon.data.auth.CredencialesInvalidasException
import com.salazar.pokemon.data.auth.DemasiadosIntentosException
import com.salazar.pokemon.domain.model.Rol
import com.salazar.pokemon.domain.model.Sesion
import com.salazar.pokemon.fakes.FakeAuthRepository
import com.salazar.pokemon.fakes.MainDispatcherRule
import com.salazar.pokemon.ui.UiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class LoginViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private val sesion = Sesion("uid", "usuario@lab.com", Rol.USUARIO)

    @Test
    fun conCamposVaciosNoLlamaAlRepositorio() {
        val repo = FakeAuthRepository(Result.success(sesion))
        val vm = LoginViewModel(repo)

        vm.ingresar("", "")

        assertEquals(UiState.Error("Ingresa el correo y la contraseña"), vm.estado.value)
        assertEquals(0, repo.intentos)
    }

    @Test
    fun conCredencialesCorrectasDevuelveLaSesion() {
        val vm = LoginViewModel(FakeAuthRepository(Result.success(sesion)))

        vm.ingresar("usuario@lab.com", "clave")

        assertEquals(UiState.Exito(sesion), vm.estado.value)
    }

    @Test
    fun conCredencialesIncorrectasMuestraEseMensaje() {
        val repo = FakeAuthRepository(Result.failure(CredencialesInvalidasException()))
        val vm = LoginViewModel(repo)

        vm.ingresar("usuario@lab.com", "mala")

        assertEquals(UiState.Error("Correo o contraseña incorrectos"), vm.estado.value)
    }

    @Test
    fun sinConexionNoSeDiceQueLaClaveEsIncorrecta() {
        val vm = LoginViewModel(FakeAuthRepository(Result.failure(IOException())))

        vm.ingresar("usuario@lab.com", "clave")

        assertEquals(UiState.Error("No hay conexión a internet"), vm.estado.value)
    }

    @Test
    fun conDemasiadosIntentosPideEsperar() {
        val vm = LoginViewModel(FakeAuthRepository(Result.failure(DemasiadosIntentosException())))

        vm.ingresar("usuario@lab.com", "clave")

        assertEquals(UiState.Error("Demasiados intentos, espera unos minutos"), vm.estado.value)
    }
}
