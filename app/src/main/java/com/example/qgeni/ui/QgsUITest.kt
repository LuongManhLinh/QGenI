//package com.example.qgeni.ui
//
//import android.util.Log
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//
//@Composable
//fun InputParagraph(
//    modifier: Modifier = Modifier,
//    qgsViewModel: QgsViewModel = viewModel()
//) {
//    val uiState by qgsViewModel.uiState.collectAsState()
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        item {
//            Column {
//                Text(
//                    text = "Enter paragraph"
//                )
//                OutlinedTextField(
//                    value = uiState.paragraph,
//                    onValueChange = {
//                        qgsViewModel.updateParagraph(it)
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//
//        }
//
//        item {
//            Column {
//                Text(
//                    text = "Enter number statements"
//                )
//                OutlinedTextField(
//                    value = uiState.numStatement,
//                    onValueChange = {
//                        qgsViewModel.updateNumStatement(it)
//                    },
//                    modifier = Modifier.width(60.dp)
//                )
//            }
//        }
//        item {
//            Button(onClick = {
//                qgsViewModel.fetchQuestions(uiState.paragraph)
//            }) {
//                Text(
//                    text = "Enter"
//                )
//            }
//        }
//        items(uiState.listQuestion) { qgsForm ->
//            Column {
//                Text(
//                    text = qgsForm.statement,
//                    style = MaterialTheme.typography.titleLarge
//                )
//                Text(
//                    text = qgsForm.answer,
//                    style = MaterialTheme.typography.titleLarge,
//                    color = Color.Red
//                )
//            }
//
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewInputParagraph() {
//    InputParagraph()
//}