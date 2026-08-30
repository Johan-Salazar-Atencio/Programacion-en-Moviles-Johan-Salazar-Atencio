package com.salazar.lab03

import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("==============================================")
    println("   SISTEMA DE CALCULO DE CUOTAS Y PAGOS JyS")
    println("==============================================")

    print("Ingrese Nombre del Producto: ")
    val producto = scanner.nextLine()

    print("Ingrese Precio del Producto : ")
    val precio = scanner.nextDouble()

    print("Ingrese Cantidad            : ")
    val cantidad = scanner.nextInt()

    print("Ingrese el N° Cuotas (6, 12 o 24): ")
    val cuotas = scanner.nextInt()

    //Agregamos el porcentaje del interes
    val pctInteres = when (cuotas) {
        6->0.20
        12->0.40
        24->0.60
        else -> {
            println("\n[ERROR] estas cuotas no son validas. Solo admitidmos 6, 12 o 24.")
            return
        }
    }
    // formulas
    val montoInicial = precio * cantidad
    val interes = montoInicial * pctInteres
    val montoAPagar = montoInicial + interes
    val pagoMensual = montoAPagar / cuotas

    println("\n----------------------------------------------")
    println("- MONTO INICIAL : S/ %.2f".format(montoInicial))
    println("- MONTO A PAGAR : S/ %.2f".format(montoAPagar))
    println("- INTERES       : S/ %.2f (%.0f%%)".format(interes, pctInteres * 100))
    println("- PAGO MENSUAL  : S/ %.2f".format(pagoMensual))
    println("----------------------------------------------")
}