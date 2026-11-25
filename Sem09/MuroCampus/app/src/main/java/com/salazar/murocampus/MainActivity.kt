package com.salazar.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.salazar.murocampus.data.PostRepositoryRemote
import com.salazar.murocampus.data.remote.RetrofitClient
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
                PantallaLista(vm, onAbrir = {})
            }
        }
    }
}
