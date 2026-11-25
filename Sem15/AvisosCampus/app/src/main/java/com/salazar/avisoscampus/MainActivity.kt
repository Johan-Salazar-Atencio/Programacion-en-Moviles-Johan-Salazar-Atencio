package com.salazar.avisoscampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salazar.avisoscampus.ui.PantallaAvisos
import com.salazar.avisoscampus.ui.theme.AvisosCampusTheme
import com.salazar.avisoscampus.viewmodel.AvisosViewModel
import com.salazar.avisoscampus.viewmodel.AvisosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvisosCampusTheme {
                val vm: AvisosViewModel = viewModel(
                    factory = AvisosViewModelFactory((application as AvisosApp).repositorio)
                )
                PantallaAvisos(vm)
            }
        }
    }
}
