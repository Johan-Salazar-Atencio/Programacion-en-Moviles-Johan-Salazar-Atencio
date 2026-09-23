package com.salazar.semana05_navegacion

import android.media.projection.AppContentRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.salazar.semana05_navegacion.navigation.AppNavigation
import com.salazar.semana05_navegacion.navigation.Screen
import com.salazar.semana05_navegacion.ui.theme.Semana05_navegacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    Semana05_navegacionTheme {
        AppNavigation()
    }
}