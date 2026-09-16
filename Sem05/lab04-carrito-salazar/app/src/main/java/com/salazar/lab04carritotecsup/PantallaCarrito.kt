package com.salazar.lab04carritotecsup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun PantallaCarrito (){
    var nombre by remember { mutableStateOf("") }
    var precio by remember {mutableStateOf("")}
    var cantidad by remember {mutableStateOf("")}

    val productos = remember { mutableStateListOf <Producto> () }

}