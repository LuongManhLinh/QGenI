package com.example.qgeni.ui.screens.practices

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.model.MockListeningPracticeItem
import com.example.qgeni.ui.theme.QGenITheme

/*
    Phần hiển thị câu hỏi và trả lời cho ListeningPracticeScreen
 */

@Composable
fun McqQuestionView(
    questions: List<McqQuestion>,
    onQuestionChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListeningPracticeViewModel
) {

    val mcqUIState by viewModel.listeningPracticeUIState.collectAsState()
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    start = 6.dp,
                    end = 6.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Question ${mcqUIState.currentQuestionIndex + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // LazyColumn hiển thị câu hỏi hiện tại
        Column(
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))

        ) {

            Text(
                text = questions[mcqUIState.currentQuestionIndex].question,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .fillMaxWidth()
            ) {
                item {
                    // Hiển thị các lựa chọn
                    questions[mcqUIState.currentQuestionIndex].answerList.forEach { option ->
                        Row(
                            modifier = Modifier
                                .clickable {
//                                    viewModel.updateSelectAnswer(option)
//                                    viewModel.updateAnsweredQuestions(mcqUIState.currentQuestionIndex, option)
                                    val newAnswer = if (mcqUIState.selectAnswer == option) null else option
                                    viewModel.updateSelectAnswer(newAnswer)
                                    viewModel.updateAnsweredQuestions(mcqUIState.currentQuestionIndex, newAnswer)
                                }
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = mcqUIState.selectAnswer == option,
                                onClick = {
                                    val newAnswer = if (mcqUIState.selectAnswer == option) null else option
                                    viewModel.updateSelectAnswer(newAnswer)
                                    viewModel.updateAnsweredQuestions(mcqUIState.currentQuestionIndex, newAnswer)
                                },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = MaterialTheme.colorScheme.tertiary,
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    disabledSelectedColor = MaterialTheme.colorScheme.primary,
                                    disabledUnselectedColor = MaterialTheme.colorScheme.tertiary
                                )
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị các ô câu hỏi bên dưới bằng LazyRow
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp)),
        ) {
            itemsIndexed(questions) { index, _ ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            when {
                                mcqUIState.currentQuestionIndex == index -> MaterialTheme.colorScheme.surfaceContainerHigh   //Style cho câu hỏi đang chọn
                                mcqUIState.answeredQuestions.containsKey(index) -> MaterialTheme.colorScheme.primary
                                else -> Color.Transparent
                            }
                        )
                        .clickable {
                            onQuestionChange(index)
                            viewModel.updateSelectAnswer(mcqUIState.answeredQuestions[index])
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = when {
                            mcqUIState.currentQuestionIndex == index -> MaterialTheme.colorScheme.onBackground   //Style cho câu hỏi đang chọn
                            mcqUIState.answeredQuestions.containsKey(index) -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.tertiary
                        }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MsqQuestionLightViewPreview() {
    QGenITheme(dynamicColor = false) {
        McqQuestionView(
            questions = MockListeningPracticeItem.listeningPracticeItem.questionList,
            onQuestionChange = {},
            viewModel = viewModel()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MsqQuestionDarkViewPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {

        McqQuestionView(
            questions = MockListeningPracticeItem.listeningPracticeItem.questionList,
            onQuestionChange = {},
            viewModel = viewModel()
        )
    }
}