package com.salazar.pokemon

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.salazar.pokemon.ui.navigation.AppNav
import com.salazar.pokemon.ui.theme.LabpokemonTheme

class MainActivity : ComponentActivity() {

    // si se niega el permiso, la app funciona igual sin mostrar avisos
    private val pedirPermisoNotificaciones =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        solicitarPermisoNotificaciones()
        val container = (application as PokemonApp).container
        setContent {
            LabpokemonTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNav(container)
                }
            }
        }
    }

    private fun solicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            pedirPermisoNotificaciones.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
