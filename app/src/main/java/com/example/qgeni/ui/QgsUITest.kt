package com.example.qgeni.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Home(qgsViewModel: QgsViewModel) {
    InputParagraph(
        onParagraphSend = {qgsViewModel.sendParagraph(it)}, qgsViewModel
    )

}

@Composable
fun InputParagraph(onParagraphSend: (String) -> Unit, qgsViewModel: QgsViewModel) {
    var message by remember { mutableStateOf("") }
    LazyColumn(modifier = Modifier.fillMaxSize().padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Button(onClick = {
                onParagraphSend(message)
//                message = ""
            }) {
                Text(
                    text = "Enter"
                )
            }
        }
        item {
            Text(
                qgsViewModel.responseText.value,
                Modifier.border(2.dp, Color.Yellow).padding(4.dp)
            )
        }
    }
}