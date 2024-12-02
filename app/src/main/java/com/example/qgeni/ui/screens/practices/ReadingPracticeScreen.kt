package com.example.qgeni.ui.screens.practices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.R
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.model.ReadingAnswer
import com.example.qgeni.ui.theme.QGenITheme

/*
    Màn hình thực hiện đề đọc
    gồm PassageView và TrueFalseQuestionView
 */

@Composable
fun ReadingPracticeScreen(
    idHexString: String,
    onBackClick: () -> Unit,
    readingPracticeViewModel: ReadingPracticeViewModel =
        viewModel(factory = ReadingPracticeViewModel.factory(idHexString))
) {

    val readingPracticeUIState by readingPracticeViewModel.uiState.collectAsState()

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
                text = "Reading",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        val passage = readingPracticeUIState.readingPracticeItem?.passage ?: ""
        val questionList = readingPracticeUIState.readingPracticeItem?.questionList ?: emptyList()
        PassageView(
            text = passage,
            modifier = Modifier.weight(1f),
            viewModel = readingPracticeViewModel
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

        TrueFalseQuestionView(
            questions = questionList.map {
                McqQuestion(
                    question = it.statement,
                    answerList = listOf(
                        ReadingAnswer.TRUE.toString(),
                        ReadingAnswer.FALSE.toString(),
                        ReadingAnswer.NOT_GIVEN.toString()),
                    correctAnswer = it.answer.toString()
                )
            },
            modifier = Modifier.weight(0.7f)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            viewModel = readingPracticeViewModel
        )
    }
    if (readingPracticeUIState.showSubmitConfirmDialog) {
        SubmitConfirm(
            onDismissRequest = {
                readingPracticeViewModel.toggleSubmitConfirmDialog(false)
            },
            onSubmitClick = {
                readingPracticeViewModel.toggleScoreDialog(true)
            },
            imageResourceId = R.drawable.reading_submit_confirm
        )
    }
    if (readingPracticeUIState.showScoreDialog) {
        DisplayScore(
            message = "10/10", // Score
            onNextButtonClick = {
                //
            },
            imageResourceId = R.drawable.reading_open_delete_confirm
        )
    }
}

@Preview
@Composable
fun ReadingPracticeScreenPreview() {
    QGenITheme(dynamicColor = false) {
         ReadingPracticeScreen(
            idHexString = "123",
            onBackClick = { }, readingPracticeViewModel = ReadingPracticeViewModel("123")
        )
    }
}

