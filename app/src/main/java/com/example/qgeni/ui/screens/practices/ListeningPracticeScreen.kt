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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.R
import com.example.qgeni.ui.screens.uploads.LoadingScreen
import com.example.qgeni.utils.formatTime

/*
    Màn hình thực hiện đề nghe, gồm ImageQuestionView và McQuestionView
 */
@Composable
fun ListeningPracticeScreen(
    idHexString: String,
    onBackClick: () -> Unit,
    onNavigatingToPracticeRepo: () -> Unit,
    viewModel: ListeningPracticeViewModel =
        viewModel(factory = ListeningPracticeViewModel.factory(idHexString))
) {

    val uiState by viewModel.uiState.collectAsState()

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
            isCompleted = uiState.isCompleted,
            currentQuestion = uiState.currentQuestionIndex,
            timeString = formatTime(uiState.time),
            imageList = uiState.imageList,
            imageLabelList = List(uiState.imageList.size) { index ->
                "Pic. " + ('A' + index)
            },
            modifier = Modifier.weight(1f),
            onPlayClick = {
                viewModel.play()
            },
            onSubmitClick = {
                if (uiState.isCompleted) {
                    onNavigatingToPracticeRepo()
                } else {
                    viewModel.toggleSubmitConfirmDialog(true)
                }
            },
            playbackState = uiState.playbackState,
            sliderPosition = uiState.audioSliderPos,
            duration = uiState.audioDuration,
            onSliderPositionChange = viewModel::updateAudioSliderPos,
            onValueChangeFinished = {
                viewModel.seekTo()
            }
        )

        if (uiState.questionList.isEmpty()) {
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
        } else {
            McqQuestionView(
                modifier = Modifier.weight(0.6f)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                questionContent = uiState.questionList[uiState.currentQuestionIndex].question,
                answerList = uiState.questionList[uiState.currentQuestionIndex].answerList,
                numQuestion = uiState.questionList.size,
                currentQuestionIdx = uiState.currentQuestionIndex,
                answeredQuestions = uiState.answeredQuestions,
                correctAnswerIdx = if (uiState.correctAnswerIds.isEmpty()) {
                    null
                } else {
                    uiState.correctAnswerIds[uiState.currentQuestionIndex]
                },
                onQuestionChange = { index ->
                    viewModel.updateCurrentQuestionIndex(index)
                },
                onAnswerSelected = { answer ->
                    viewModel.updateAnsweredQuestions(uiState.currentQuestionIndex, answer)
                },
            )
        }
    }

    if (uiState.showSubmitConfirmDialog) {
        SubmitConfirm(
            onDismissRequest = {
                viewModel.toggleSubmitConfirmDialog(false)
            },
            onSubmitClick = {
                viewModel.toggleScoreDialog(true)
            },
            imageResourceId = R.drawable.listening_submit_confirm
        )
    }

    if (uiState.showScoreDialog) {
        DisplayScore(
            message = "${uiState.numCorrect}/${uiState.questionList.size}",
            onNextButtonClick = onNavigatingToPracticeRepo,
            onDismissRequest = {
                viewModel.toggleScoreDialog(false)
            },
            imageResourceId = R.drawable.listening_open_delete_confirm
        )
    }

    if (uiState.showLoadingDialog) {
        LoadingScreen(
            message = "Đang tải đề...",
            onStopClicked = {}
        )
    }

}
