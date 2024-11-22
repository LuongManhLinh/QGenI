package com.example.qgeni

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.ui.InputParagraph
import com.example.qgeni.ui.screens.navigation.QGNavHost
import com.example.qgeni.ui.theme.QGenITheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QGenITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    val navController = rememberNavController()
//                    QGNavHost(
//                        modifier = Modifier.padding(innerPadding),
//                        navController = navController,
//                        currentTheme = ThemeMode.DARK,
//                        onThemeChange = {
//                        }
//                    )
                    InputParagraph(Modifier.padding(innerPadding))
                }
            }
        }
    }
}



