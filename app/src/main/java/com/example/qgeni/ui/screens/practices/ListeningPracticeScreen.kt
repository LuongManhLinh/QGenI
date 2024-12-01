package com.example.qgeni.ui.screens.practices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.ui.screens.utils.formatTime

/*
    Màn hình thực hiện đề nghe, gồm ImageQuestionView và McQuestionView
 */
@Composable
fun ListeningPracticeScreen(
    idHexString: String,
    onBackClick: () -> Unit,
    viewModel: ListeningPracticeViewModel =
        viewModel(factory = ListeningPracticeViewModel.factory(idHexString))
) {

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

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

        val imageList = if (uiState.listeningPracticeItem != null) {
            uiState.listeningPracticeItem!!.questionList[uiState.currentQuestionIndex].imageList
        } else {
            emptyList()
        }

        val questionList = if (uiState.listeningPracticeItem != null) {
            uiState.listeningPracticeItem!!.questionList
        } else {
            emptyList()
        }


        ImageQuestionView(
            currentQuestion = uiState.currentQuestionIndex,
            timeString = formatTime(uiState.time),
            imageList = imageList,
            imageLabelList = List(imageList.size) { index ->
                "Pic. " + ('A' + index)
            },
            modifier = Modifier.weight(1f),
            onPlayClick = {
                viewModel.play(context)
            },
            onSubmitClick = viewModel::submit
        )

        if (questionList.isEmpty()) {
            Box( modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Transparent)
                .fillMaxWidth()
                .weight(0.7f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
            )

            return
        }

        McqQuestionView(
            modifier = Modifier.weight(0.6f)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            questions = questionList.map {
                McqQuestion(
                    question = "Choose the correct picture",
                    answerList = List(it.imageList.size) { index ->
                        ('A' + index).toString()
                    },
                    correctAnswer = ('A' + it.answerIndex).toString()
                )
            },
            currentQuestionIdx = uiState.currentQuestionIndex,
            answeredQuestions = uiState.answeredQuestions,
            onQuestionChange = { index ->
                viewModel.updateCurrentQuestionIndex(index)
            },
            onAnswerSelected = { answer ->
                viewModel.updateAnsweredQuestions(uiState.currentQuestionIndex, answer)
            },


        )
    }
}
