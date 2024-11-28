package com.example.qgeni

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.qgeni.data.preferences.ThemeMode
import com.example.qgeni.data.preferences.ThemePreferenceManager
import com.example.qgeni.data.repository.DefaultListeningRepository
import com.example.qgeni.ui.ExampleIdsUI
import com.example.qgeni.ui.screens.navigation.QGNavHost
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import kotlin.reflect.typeOf

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
//                    DemoImages(modifier = Modifier.padding(innerPadding))
                    ExampleIdsUI(Modifier.padding(innerPadding))
//                    val navController = rememberNavController()
//                    QGNavHost(
//                        modifier = Modifier.padding(innerPadding),
//                        navController = navController,
//                        currentTheme = currentTheme,
//                        onThemeChange = { newTheme ->
//                            CoroutineScope(Dispatchers.IO).launch {
//                                themePreferenceManager.saveTheme(newTheme.name)
//                            }
//                        }
//                    )
                }
            }
        }
    }
}

@Composable
private fun DemoImages(modifier: Modifier) {
    var imgList by remember { mutableStateOf(emptyList<Bitmap>()) }

    var descList by remember { mutableStateOf(emptyList<String>()) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        coroutineScope.launch(Dispatchers.IO) {
            val listeningItem = DefaultListeningRepository.getAll(ObjectId("67473ce7c98e8377b17630c9"))
            imgList = listeningItem.first().images
            descList = listeningItem.first().answers
        }
    }

    LazyColumn(modifier = modifier) {
        items(imgList.size) { index ->
            val img = imgList[index]
            val desc = descList[index]

            Text(
                text = desc,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(4.dp)
            )
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        bottom = 48.dp
                    ),
                contentScale = ContentScale.FillWidth
            )
        }
    }

}









