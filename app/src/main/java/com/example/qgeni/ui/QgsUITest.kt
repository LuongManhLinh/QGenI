package com.example.qgeni.ui

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Home() {
    InputParagraph()
}

@Composable
fun InputParagraph(qgsViewModel: QgsViewModel = viewModel()) {
    val uiState by qgsViewModel.uiState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            OutlinedTextField(
                value = uiState.paragraph,
                onValueChange = {
                    qgsViewModel.updateParagraph(it)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Button(onClick = {
                qgsViewModel.fetchQuestions(uiState.paragraph)
            }) {
                Text(
                    text = "Enter"
                )
            }
        }
        item {
            uiState.listQuestion.forEach { question ->
                Text(
                    text = question.statement
                )

                Log.i("Display", question.statement)
            }
        }
//        Log.i("DisplayText", uiState.listQuestion[0].statement)
    }
}