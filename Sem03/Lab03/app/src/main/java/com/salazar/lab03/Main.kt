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
}