package com.example.qgeni.ui.screens.practices

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.MockListeningPracticeItem
import com.example.qgeni.ui.theme.QGenITheme

/*
    Màn hình thực hiện đề nghe, gồm ImageQuestionView và McQuestionView
 */

@Composable
fun ListeningPracticeScreen(
    listeningPracticeItem: ListeningPracticeItem,
    onBackClick: () -> Unit,
    listeningPracticeViewModel: ListeningPracticeViewModel = viewModel()
) {
//    var currentQuestionIndex by remember { mutableStateOf(0) }
//    val answeredQuestions = remember { mutableStateMapOf<Int, String>() }

    val listeningUIState by listeningPracticeViewModel.listeningPracticeUIState.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "BackIcon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.7f))
            Text(
                text = "Listening",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        ImageQuestionView(
            currentQuestion = listeningUIState.currentQuestionIndex,
            record = "null",
            imageList = listeningPracticeItem.imageList[listeningUIState.currentQuestionIndex],
            modifier = Modifier.weight(1f),
            onPlayClick = {
                //Làm gì đó
            },
            viewModel = listeningPracticeViewModel
        )
        McqQuestionView(
            questions = listeningPracticeItem.questionList,
//            currentQuestionIndex = listeningUIState.currentQuestionIndex,
//            answeredQuestions = listeningUIState.answeredQuestions,
            onQuestionChange = { index ->
                listeningPracticeViewModel.updateCurrentQuestionIndex(index)
            },
            modifier = Modifier.weight(0.6f)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            viewModel = listeningPracticeViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningPracticeLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ListeningPracticeScreen(
            listeningPracticeItem =
            MockListeningPracticeItem.listeningPracticeItem,
            {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningPracticeDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ListeningPracticeScreen(
            listeningPracticeItem =
            MockListeningPracticeItem.listeningPracticeItem,
            {}
        )
    }
}