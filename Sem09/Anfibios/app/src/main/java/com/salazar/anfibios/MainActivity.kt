package com.salazar.anfibios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.salazar.anfibios.ui.theme.AnfibiosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnfibiosTheme {
                Text(text = "Anfibios")
            }
        }
    }
}