package com.salazar.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salazar.murocampus.data.PostRepositoryRemote
import com.salazar.murocampus.data.remote.RetrofitClient
import com.salazar.murocampus.ui.PantallaDetalle
import com.salazar.murocampus.ui.PantallaLista
import com.salazar.murocampus.ui.theme.MuroCampusTheme
import com.salazar.murocampus.viewmodel.PostsViewModel
import com.salazar.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuroCampusTheme {
                val vm: PostsViewModel = viewModel(
                    factory = PostsViewModelFactory(PostRepositoryRemote(RetrofitClient.api))
                )
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }

                BackHandler(enabled = idSeleccionado != null) { idSeleccionado = null }

                val id = idSeleccionado
                if (id == null) {
                    PantallaLista(vm, onAbrir = {
                        idSeleccionado = it
                        vm.abrir(it)
                    })
                } else {
                    PantallaDetalle(vm, id, onVolver = { idSeleccionado = null })
                }
            }
        }
    }
}
