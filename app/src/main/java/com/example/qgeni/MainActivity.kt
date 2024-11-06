package com.example.qgeni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.qgeni.ui.ExampleIdsUI
import com.example.qgeni.ui.theme.QGenITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QGenITheme {
                ExampleIdsUI()
            }
        }
    }
}



