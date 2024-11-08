package com.example.qgeni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.qgeni.ui.ExampleIdsUI
import com.example.qgeni.ui.Home
import com.example.qgeni.ui.QgsViewModel
import com.example.qgeni.ui.theme.QGenITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val qgsViewModel = ViewModelProvider(this)[QgsViewModel::class.java]
        setContent {
            QGenITheme {
                ExampleIdsUI()
//                Home(qgsViewModel)
            }
        }
    }
}



