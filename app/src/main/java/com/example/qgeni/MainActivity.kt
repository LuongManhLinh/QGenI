package com.example.qgeni

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.data.preferences.ThemePreferenceManager
import com.example.qgeni.ui.ExampleIdsUI
import com.example.qgeni.ui.Home
import com.example.qgeni.ui.InputParagraph
import com.example.qgeni.ui.QgsViewModel
import com.example.qgeni.ui.screens.navigation.QGNavHost
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QGenITheme(
                dynamicColor = false,
                darkTheme = true
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    QGNavHost(
                        navController = navController,
                        currentTheme = ThemeMode.DARK,
                        onThemeChange = {
                        }
                    )
                }
            }
        }
    }
}



