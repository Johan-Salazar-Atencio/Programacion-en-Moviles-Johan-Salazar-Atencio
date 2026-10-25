package com.salazar.cafetecsup.ui.cliente

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salazar.cafetecsup.R

private val FondoSplash = Color(0xFFFFF4E8)
private val NaranjaSplash = Color(0xFFEA580C)
private val TextoOscuro = Color(0xFF1F2933)
private val TextoGris = Color(0xFF7B8794)

@Composable
fun SplashScreen(
    onComenzar: () -> Unit,
    onYaTengoCuenta: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSplash)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(NaranjaSplash),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_taza_logo),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("CafeCampus", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
        Text("Pide sin hacer cola", fontSize = 14.sp, color = TextoGris, modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagenSplash(R.drawable.cafe_americano, 1f)
            ImagenSplash(R.drawable.menu_dia, 0.75f)
            ImagenSplash(R.drawable.jugo_naranja, 1f)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onComenzar,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NaranjaSplash, contentColor = Color.White)
        ) {
            Text("Comenzar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = onYaTengoCuenta, modifier = Modifier.padding(top = 4.dp)) {
            Text("Ya tengo una cuenta", color = NaranjaSplash, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ImagenSplash(recurso: Int, opacidad: Float) {
    Image(
        painter = painterResource(recurso),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(width = 72.dp, height = 84.dp)
            .alpha(opacidad)
            .clip(RoundedCornerShape(16.dp))
    )
}
