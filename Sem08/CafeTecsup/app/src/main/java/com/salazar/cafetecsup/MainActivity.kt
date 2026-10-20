package com.salazar.cafetecsup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.salazar.cafetecsup.data.local.CafeDatabase
import com.salazar.cafetecsup.data.prefs.PreferenciasStore
import com.salazar.cafetecsup.data.repository.CafeRepositoryRoom
import com.salazar.cafetecsup.ui.navigation.NavCafe
import com.salazar.cafetecsup.ui.theme.CafetecsupTheme
import com.salazar.cafetecsup.viewmodel.MenuViewModel
import com.salazar.cafetecsup.viewmodel.PedidosClienteViewModel
import com.salazar.cafetecsup.viewmodel.SesionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = androidx.room.Room.databaseBuilder(
            applicationContext,
            CafeDatabase::class.java,
            "cafecampus.db"
        ).build()
        val repository = CafeRepositoryRoom(database)
        val prefs = PreferenciasStore(this)

        val sesionViewModel = SesionViewModel(repository, prefs)
        val menuViewModel = MenuViewModel(repository)
        val pedidosClienteViewModel = PedidosClienteViewModel(repository)

        enableEdgeToEdge()
        setContent {
            CafetecsupTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavCafe(
                        viewModel = sesionViewModel,
                        menuViewModel = menuViewModel,
                        pedidosViewModel = pedidosClienteViewModel
                    )
                }
            }
        }
    }
}