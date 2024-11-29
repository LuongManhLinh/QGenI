package com.example.qgeni.ui.screens.practices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.model.ReadingAnswer
import com.example.qgeni.data.model.ReadingPracticeItem

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

    val readingPracticeUIState by readingPracticeViewModel.readingPracticeUIState.collectAsState()

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
}


