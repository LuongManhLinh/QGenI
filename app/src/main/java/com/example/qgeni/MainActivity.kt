package com.example.qgeni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.qgeni.ui.ExampleIdsUI
import com.example.qgeni.ui.Home
import com.example.qgeni.ui.InputParagraph
import com.example.qgeni.ui.QgsViewModel
import com.example.qgeni.ui.theme.QGenITheme
import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QGenITheme {
//                ExampleIdsUI()
                InputParagraph()
            }
        }
    }
}

//@Composable
//fun RadioButtonGroup() {
//    var selectedOption by remember { mutableStateOf("True") }
//
//    Column(
//        modifier = Modifier.padding(16.dp)
//    ) {
//        Text("Select an option:")
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = selectedOption == "True",
//                onClick = { selectedOption = "True" }
//            )
//            Text("True")
//        }
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = selectedOption == "False",
//                onClick = { selectedOption = "False" }
//            )
//            Text("False")
//        }
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            RadioButton(
//                selected = selectedOption == "Not Given",
//                onClick = { selectedOption = "Not Given" }
//            )
//            Text("Not Given")
//        }
//    }
//}
//
//@Composable
//fun MyApp() {
//    MaterialTheme {
//        RadioButtonGroup()
//    }
//}







