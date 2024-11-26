package com.example.qgeni.ui.screens.practices

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.qgeni.data.model.MockReadingPracticeItem
import com.example.qgeni.ui.theme.QGenITheme

/*
    Phần hiển thị câu hỏi và trả lời cho ReadingPracticeScreen
 */

@Composable
fun TrueFalseQuestionView(
    questions: List<McqQuestion>,
//    answeredQuestions: MutableMap<Int, String>,
    modifier: Modifier = Modifier,
    viewModel: ReadingPracticeViewModel
) {
//    var selectedAnswer by remember { mutableStateOf<String?>(null) }
//    var currentQuestionIndex by remember { mutableIntStateOf(0) }

    val tfqUIState by viewModel.readingPracticeUIState.collectAsState()
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
//                text = "Question ${currentQuestionIndex + 1}",
                text = "Question ${tfqUIState.currentQuestionIndex + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // LazyColumn hiển thị câu hỏi hiện tại
        LazyColumn(
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
            item {
                Text(
//                    text = questions[currentQuestionIndex].question,
                    text = questions[tfqUIState.currentQuestionIndex].question,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Hiển thị các lựa chọn True/False/Not Given
//                questions[currentQuestionIndex].answerList.forEach { option ->
                questions[tfqUIState.currentQuestionIndex].answerList.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
//                                selectedAnswer = option
//                                answeredQuestions[currentQuestionIndex] = option
                                viewModel.updateSelectAnswer(option)
                                viewModel.updateAnsweredQuestions(tfqUIState.currentQuestionIndex, option)
                            }
                            .height(30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
//                            selected = selectedAnswer == option,
                            selected = tfqUIState.selectAnswer == option,
                            onClick = {
                                val newSelected = if (tfqUIState.selectAnswer == option) null else option
                                viewModel.updateSelectAnswer(newSelected)
                                viewModel.updateAnsweredQuestions(tfqUIState.currentQuestionIndex, newSelected)
//                                if (selectedAnswer != null) {
//                                    answeredQuestions[currentQuestionIndex] = selectedAnswer!!
//                                } else {
//                                    answeredQuestions.remove(currentQuestionIndex)
//                                }
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
                            modifier = Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
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
                                tfqUIState.currentQuestionIndex == index -> MaterialTheme.colorScheme.surfaceContainerHigh // Style cho câu hỏi đang chọn
                                tfqUIState.answeredQuestions.containsKey(index) -> MaterialTheme.colorScheme.primary
                                else -> Color.Transparent
                            }
                        )
                        .clickable {
//                            currentQuestionIndex = index
//                            selectedAnswer = answeredQuestions[index]
                            viewModel.updateCurrentQuestionIndex(index)
                            viewModel.updateSelectAnswer(tfqUIState.answeredQuestions[index])

                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = when {
                            tfqUIState.currentQuestionIndex == index -> MaterialTheme.colorScheme.onBackground // Style cho câu hỏi đang chọn
                            tfqUIState.answeredQuestions.containsKey(index) -> MaterialTheme.colorScheme.onPrimary
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


//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun TrueFalseQuestionLightViewPreview() {
//    QGenITheme(dynamicColor = false) {
//        val answeredQuestions = remember { mutableStateMapOf<Int, String>() }
//        TrueFalseQuestionView(
//            questions = MockReadingPracticeItem.readingPracticeItem.questionList,
////            answeredQuestions = answeredQuestions,
//            viewModel = viewModel()
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun TrueFalseQuestionDarkViewPreview() {
//    QGenITheme(dynamicColor = false, darkTheme = true) {
//        val answeredQuestions = remember { mutableStateMapOf<Int, String>() }
//        TrueFalseQuestionView(
//            questions = MockReadingPracticeItem.readingPracticeItem.questionList,
////            answeredQuestions = answeredQuestions,
//            viewModel = viewModel()
//        )
//    }
//}