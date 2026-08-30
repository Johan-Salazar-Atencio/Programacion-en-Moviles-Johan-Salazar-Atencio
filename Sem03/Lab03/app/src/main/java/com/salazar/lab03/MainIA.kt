package com.salazar.lab03

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("=== SISTEMA DE FINANCIAMIENTO DE PRODUCTOS ===")

    // 1. ENTRADA DE DATOS
    print("Ingrese el nombre del producto: ")
    val nombreProducto = scanner.nextLine()

    print("Ingrese el precio del producto: ")
    val precio = scanner.nextDouble()

    print("Ingrese la cantidad: ")
    val cantidad = scanner.nextInt()

    print("Ingrese el número de cuotas (6, 12 o 24): ")
    val numCuotas = scanner.nextInt()

    // 2. REGLA DE NEGOCIO (Tasa de Interés con 'when')
    val porcentajeInteres: Double = when (numCuotas) {
        6 -> 0.20
        12 -> 0.40
        24 -> 0.60
        else -> {
            println("\n[ERROR] El número de cuotas ingresado ($numCuotas) no es válido. Solo se permite financiamiento a 6, 12 o 24 cuotas.")
            return // Finaliza la ejecución del programa
        }
    }

    // 3. FÓRMULAS DE CÁLCULO FINANCIERO
    val montoInicial = precio * cantidad
    val montoInteres = montoInicial * porcentajeInteres
    val montoTotalAPagar = montoInicial + montoInteres
    val pagoMensual = montoTotalAPagar / numCuotas

    // 4. SALIDA Y RESUMEN INICIAL
    println("\n" + "=".repeat(50))
    println("              RESUMEN DE FINANCIAMIENTO")
    println("=".repeat(50))
    println("Producto              : $nombreProducto")
    println("Precio Unitario       : S/ %.2f".format(precio))
    println("Cantidad              : $cantidad")
    println("Monto Inicial         : S/ %.2f".format(montoInicial))
    println("Porcentaje de Interés : %.0f%%".format(porcentajeInteres * 100))
    println("Monto de Interés      : S/ %.2f".format(montoInteres))
    println("Monto Total a Pagar   : S/ %.2f".format(montoTotalAPagar))
    println("Número de Cuotas      : $numCuotas")
    println("Pago Mensual          : S/ %.2f".format(pagoMensual))
    println("=".repeat(50))

    // 5. CRONOGRAMA DE PAGOS
    println("\nCRONOGRAMA DE PAGOS / TABLA DE AMORTIZACIÓN")
    println("-".repeat(60))
    // Encabezado alineado con formato de columnas
    println("%-4s | %-12s | %-12s | %-12s | %-12s".format("N°", "FECHA", "MONTO", "PMENSUAL", "RESTA PAGO"))
    println("-".repeat(60))

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    var saldoPendiente = montoTotalAPagar

    for (i in 1..numCuotas) {
        // Avanza 1 mes en cada iteración a partir de la fecha actual
        calendar.add(Calendar.MONTH, 1)
        val fechaFormateada = dateFormat.format(calendar.time)

        val saldoAnterior = saldoPendiente

        // Ajuste para evitar residuos imprecisos por decimales en la última cuota
        saldoPendiente = if (i == numCuotas) {
            0.00
        } else {
            saldoPendiente - pagoMensual
        }

        println(
            "%-4d | %-12s | S/ %-9.2f | S/ %-9.2f | S/ %-9.2f".format(
                i,
                fechaFormateada,
                saldoAnterior,
                pagoMensual,
                saldoPendiente
            )
        )
    }
    println("-".repeat(60))
}