package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.Usuario
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.categorias.CategoriasScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.cliente.screens.terminos.TerminosScreen

/**
 * "Director de orquesta" de la app cliente:
 * - Tiene el NavHost con las rutas de cada pantalla.
 * - Maneja el estado global reactivo del carrito y de productos favoritos.
 */
private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val INICIO = "inicio"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val DATOS_ENTREGA = "datos_entrega"
    const val CONFIRMACION = "confirmacion"
    const val CATEGORIAS = "categorias"
    const val PEDIDOS = "pedidos"
    const val PERFIL = "perfil"
    const val TERMINOS = "terminos"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    // Estados reactivos globales
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var favoritosIds by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var usuario by remember { mutableStateOf<Usuario?>(null) }

    fun toggleFavorito(productoId: Int) {
        favoritosIds = if (favoritosIds.contains(productoId)) {
            favoritosIds - productoId
        } else {
            favoritosIds + productoId
        }
    }

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA
    ) {
        composable(Rutas.BIENVENIDA) {
            BienvenidaScreen(
                onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                onIniciarSesion = { navController.navigate(Rutas.REGISTRO) },
                onTerminos = { navController.navigate(Rutas.TERMINOS) }
            )
        }

        composable(Rutas.REGISTRO) {
            RegistroScreen(
                onVolver = { navController.popBackStack() },
                onCrearCuenta = { nombre, telefono, direccion, referencia ->
                    usuario = Usuario(nombre, telefono, direccion, referencia)
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            InicioScreen(
                cantidadCarrito = carrito.sumOf { it.cantidad },
                favoritosIds = favoritosIds,
                onToggleFavorito = { id -> toggleFavorito(id) },
                onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                onProductoClick = { producto ->
                    navController.navigate(Rutas.detalle(producto.id))
                },
                onAgregarProducto = { producto ->
                    carrito = agregarOSumarProducto(carrito, producto, 1)
                },
                onNavegar = { indice ->
                    when (indice) {
                        0 -> navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                        }
                        1 -> navController.navigate(Rutas.CATEGORIAS)
                        2 -> navController.navigate(Rutas.PEDIDOS)
                        3 -> navController.navigate(Rutas.PERFIL)
                    }
                }
            )
        }

        composable(
            route = Rutas.DETALLE,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            val producto = listaProductosFake.first { it.id == productoId }
            val esFav = favoritosIds.contains(producto.id)

            DetalleProductoScreen(
                producto = producto,
                esFavorito = esFav,
                onToggleFavorito = { toggleFavorito(producto.id) },
                onVolver = { navController.popBackStack() },
                onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                    carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                    navController.popBackStack()
                }
            )
        }

        composable(Rutas.CARRITO) {
            CarritoScreen(
                carrito = carrito,
                onVolver = { navController.popBackStack() },
                onIncrementar = { producto ->
                    carrito = carrito.map {
                        if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                    }
                },
                onDecrementar = { producto ->
                    carrito = carrito.mapNotNull {
                        when {
                            it.producto.id != producto.id -> it
                            it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                            else -> null
                        }
                    }
                },
                onEliminar = { producto ->
                    carrito = carrito.filterNot { it.producto.id == producto.id }
                },
                onContinuarPedido = { navController.navigate(Rutas.DATOS_ENTREGA) }
            )
        }

        composable(Rutas.DATOS_ENTREGA) {
            val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
            val costoEnvio = if (carrito.isNotEmpty()) 5.0 else 0.0
            val totalCalculado = subtotal + costoEnvio

            DatosEntregaScreen(
                montoTotal = totalCalculado,
                direccionInicial = usuario?.direccion ?: "",
                referenciaInicial = usuario?.referencia ?: "",
                onVolver = { navController.popBackStack() },
                onConfirmarPedido = { _, _, _ ->
                    carrito = emptyList()
                    navController.navigate(Rutas.CONFIRMACION) {
                        popUpTo(Rutas.INICIO) { inclusive = false }
                    }
                }
            )
        }

        composable(Rutas.CONFIRMACION) {
            ConfirmacionScreen(
                onVolverInicio = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.INICIO) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.CATEGORIAS) {
            CategoriasScreen(
                onNavegar = { indice ->
                    when (indice) {
                        0 -> navController.navigate(Rutas.INICIO)
                        1 -> navController.navigate(Rutas.CATEGORIAS) {
                            popUpTo(Rutas.CATEGORIAS) { inclusive = true }
                        }
                        2 -> navController.navigate(Rutas.PEDIDOS)
                        3 -> navController.navigate(Rutas.PERFIL)
                    }
                }
            )
        }

        composable(Rutas.PEDIDOS) {
            PedidosScreen(
                onNavegar = { indice ->
                    when (indice) {
                        0 -> navController.navigate(Rutas.INICIO)
                        1 -> navController.navigate(Rutas.CATEGORIAS)
                        2 -> navController.navigate(Rutas.PEDIDOS) {
                            popUpTo(Rutas.PEDIDOS) { inclusive = true }
                        }
                        3 -> navController.navigate(Rutas.PERFIL)
                    }
                }
            )
        }

        composable(Rutas.PERFIL) {
            PerfilScreen(
                onNavegar = { indice ->
                    when (indice) {
                        0 -> navController.navigate(Rutas.INICIO)
                        1 -> navController.navigate(Rutas.CATEGORIAS)
                        2 -> navController.navigate(Rutas.PEDIDOS)
                        3 -> navController.navigate(Rutas.PERFIL) {
                            popUpTo(Rutas.PERFIL) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Rutas.TERMINOS) {
            TerminosScreen(onVolver = { navController.popBackStack() })
        }
    }
}

/**
 * Si el producto ya está en el carrito, le suma la cantidad;
 * si no, lo agrega como un ItemCarrito nuevo.
 */
private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}
