package com.salazar.centrotareas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salazar.centrotareas.data.TareasRepositoryLocal
import com.salazar.centrotareas.ui.PantallaTareas
import com.salazar.centrotareas.ui.theme.CentroTareasTheme
import com.salazar.centrotareas.viewmodel.TareasViewModel
import com.salazar.centrotareas.viewmodel.TareasViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CentroTareasTheme {
                val vm: TareasViewModel = viewModel(
                    factory = TareasViewModelFactory(TareasRepositoryLocal())
                )
                PantallaTareas(vm)
            }
        }
    }
}
