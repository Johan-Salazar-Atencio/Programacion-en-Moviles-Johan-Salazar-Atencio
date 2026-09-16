package com.salazar.lab04carritotecsup

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TarjetaProducto(producto: Producto, onEliminar: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column{
            Text(producto.nombre, style = MaterialTheme. typography.titleMedium )
            Text("Precio: S/ ${producto. precio}")
        }
        
        //Boton de eliminar 
        Button(
            onClick = {onEliminar()},
            colors = ButtonDefaults. buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("X")
        }
    }
}