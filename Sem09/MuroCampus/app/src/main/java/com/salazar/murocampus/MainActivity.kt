package com.salazar.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.salazar.murocampus.ui.BarraMuro
import com.salazar.murocampus.ui.theme.MuroCampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuroCampusTheme {
                Scaffold(topBar = { BarraMuro("MuroCampus") }) { relleno ->
                    Box(Modifier.padding(relleno))
                }
            }
        }
    }
}
