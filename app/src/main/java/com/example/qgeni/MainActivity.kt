package com.example.qgeni

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.data.preferences.ThemePreferenceManager
import com.example.qgeni.ui.screens.navigation.QGNavHost
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var themePreferenceManager: ThemePreferenceManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themePreferenceManager = ThemePreferenceManager(this)
        enableEdgeToEdge()
        setContent {
            val themeMode = themePreferenceManager.themeFlow.collectAsState(initial = ThemeMode.SYSTEM.name)
            val currentTheme = remember(themeMode.value) {
                ThemeMode.valueOf(themeMode.value)
            }

            val isDarkTheme = when (currentTheme) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            QGenITheme(dynamicColor = false, darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    QGNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        currentTheme = currentTheme,
                        onThemeChange = { newTheme ->
                            CoroutineScope(Dispatchers.IO).launch {
                                themePreferenceManager.saveTheme(newTheme.name)
                            }
                        }
                    )
                }
            }
        }
    }
}









