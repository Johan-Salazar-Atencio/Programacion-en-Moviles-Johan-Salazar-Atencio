package com.salazar.lab02carritokotlin
import java.util.Locale

// 1. CLASE PRODUCTOS (Model Data)
class Productos(
    val id: Int,
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

// 2. CLASE CARRITO DE COMPRAS (Lógica de Negocio)
class CarritoDeCompras {
    private val listaProductos = mutableListOf<Productos>()

    fun agregarProducto(producto: Productos) {
        listaProductos.add(producto)
    }

    // Buscar producto por nombre utilizando .find
    fun buscarProducto(nombre: String): Productos? {
        return listaProductos.find { it.nombre.equals(nombre, ignoreCase = true) }
    }

    // Eliminar producto utilizando .removeIf
    fun eliminarProducto(nombre: String): Boolean {
        return listaProductos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
    }

    // Encontrar el producto más caro utilizando maxByOrNull
    fun obtenerProductoMasCaro(): Productos? {
        return listaProductos.maxByOrNull { it.precio }
    }

    // Cálculos económicos
    fun calcularSubtotal(): Double {
        return listaProductos.sumOf { it.precio * it.cantidad }
    }

    fun calcularDescuento(subtotal: Double): Double {
        val porcentajeDescuento = when {
            subtotal > 5000 -> 0.10
            subtotal > 3000 -> 0.05
            else -> 0.00
        }
        return subtotal * porcentajeDescuento
    }

    fun calcularIgv(montoConDescuento: Double): Double {
        return montoConDescuento * 0.18
    }

    // Impresión detallada usando String.format con Locale.US
    fun mostrarDetalle() {
        if (listaProductos.isEmpty()) {
            println("\n--- EL CARRITO ESTÁ VACÍO ---")
            return
        }

        val subtotal = calcularSubtotal()
        val descuento = calcularDescuento(subtotal)
        val subtotalConDescuento = subtotal - descuento
        val igv = calcularIgv(subtotalConDescuento)
        val total = subtotalConDescuento + igv

        println("\n=======================================================")
        println("                 CARRITO CON IA                        ")
        println("=======================================================")
        println(String.format(Locale.US, "%-5s | %-18s | %-8s | %-5s | %-10s", "ID", "Producto", "Precio", "Cant.", "Total"))
        println("-------------------------------------------------------")

        for (prod in listaProductos) {
            val totalItem = prod.precio * prod.cantidad
            println(
                String.format(
                    Locale.US,
                    "%-5d | %-18s | $%-7.2f | %-5d | $%-10.2f",
                    prod.id,
                    prod.nombre,
                    prod.precio,
                    prod.cantidad,
                    totalItem
                )
            )
        }

        println("-------------------------------------------------------")
        println(String.format(Locale.US, "%-40s: $%.2f", "Subtotal", subtotal))
        println(String.format(Locale.US, "%-40s: -$%.2f", "Descuento", descuento))
        println(String.format(Locale.US, "%-40s: $%.2f", "IGV (18%)", igv))
        println(String.format(Locale.US, "%-40s: $%.2f", "TOTAL A PAGAR", total))
        println("=======================================================\n")
    }
}

// 3. FUNCIÓN PRINCIPAL (Demostración de uso)
fun main() {
    val carrito = CarritoDeCompras()

    // a. Cargar productos iniciales usando la clase Productos
    carrito.agregarProducto(Productos(1, "Laptop Gamer", 3500.00, 1))
    carrito.agregarProducto(Productos(2, "Monitor 4K", 1200.00, 2))
    carrito.agregarProducto(Productos(3, "Mouse Inalámbrico", 150.00, 2))
    carrito.agregarProducto(Productos(4, "Teclado Mecánico", 350.00, 1))

    // b. Mostrar el detalle inicial con sus totales
    println(">>> 1. DETALLE INICIAL DEL CARRITO <<<")
    carrito.mostrarDetalle()

    // Buscar el producto más caro
    val masCaro = carrito.obtenerProductoMasCaro()
    if (masCaro != null) {
        println(String.format(Locale.US, "🔥 Producto más caro: %s ($%.2f)", masCaro.nombre, masCaro.precio))
    }

    // c. Buscar un producto por nombre (.find)
    println("\n>>> 2. BÚSQUEDA DE PRODUCTO <<<")
    val nombreBusqueda = "Monitor 4K"
    val productoEncontrado = carrito.buscarProducto(nombreBusqueda)
    if (productoEncontrado != null) {
        println("🔍 Producto encontrado: ${productoEncontrado.nombre} - Precio: $${productoEncontrado.precio}")
    } else {
        println("❌ Producto '$nombreBusqueda' no encontrado.")
    }

    // d. Eliminar un producto (.removeIf)
    println("\n>>> 3. ELIMINACIÓN DE PRODUCTO <<<")
    val nombreEliminar = "Teclado Mecánico"
    val eliminado = carrito.eliminarProducto(nombreEliminar)
    if (eliminado) {
        println("🗑️ Se eliminó '$nombreEliminar' del carrito exitosamente.")
    } else {
        println("❌ No se pudo eliminar '$nombreEliminar'.")
    }

    // e. Volver a imprimir el detalle con totales actualizados
    println("\n>>> 4. DETALLE FINAL DEL CARRITO <<<")
    carrito.mostrarDetalle()
}