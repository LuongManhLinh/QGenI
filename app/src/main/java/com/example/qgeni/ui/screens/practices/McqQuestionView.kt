package com.example.qgeni.ui.screens.practices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.ui.theme.QGenITheme

/*
    Phần hiển thị câu hỏi và trả lời cho ListeningPracticeScreen
 */

@Composable
fun McqQuestionView(
    modifier: Modifier = Modifier,
    questions: List<McqQuestion>,
    currentQuestionIdx: Int,
    answeredQuestions: Map<Int, Int?>,
    onQuestionChange: (Int) -> Unit,
    onAnswerSelected: (Int) -> Unit,
) {

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
                text = "Question ${currentQuestionIdx + 1}",
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
                .weight(0.7f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))

        ) {

            Text(
                text = questions[currentQuestionIdx].question,
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
                    questions[currentQuestionIdx].answerList.forEachIndexed { answerIdx,  option ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onAnswerSelected(answerIdx)
                                }
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = answerIdx == answeredQuestions[currentQuestionIdx],
                                onClick = {
                                    onAnswerSelected(answerIdx)
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
                                currentQuestionIdx == index -> MaterialTheme.colorScheme.surfaceContainerHigh   //Style cho câu hỏi đang chọn
                                answeredQuestions[index] != null -> MaterialTheme.colorScheme.primary
                                else -> Color.Transparent
                            }
                        )
                        .clickable {
                            onQuestionChange(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = when {
                            currentQuestionIdx == index -> MaterialTheme.colorScheme.onBackground   //Style cho câu hỏi đang chọn
                            answeredQuestions[index] != null -> MaterialTheme.colorScheme.onPrimary
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



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MsqQuestionLightViewPreview() {
    QGenITheme(dynamicColor = false) {
        McqQuestionView(
            questions = listOf(
                McqQuestion(
                    question = "What is the capital of France?",
                    answerList = listOf("Paris", "London", "Berlin", "Madrid"),
                ),
                McqQuestion(
                    question = "What is the capital of Germany?",
                    answerList = listOf("Paris", "London", "Berlin", "Madrid"),
                ),
            ),
            currentQuestionIdx = 0,
            onQuestionChange = {},
            onAnswerSelected = {},
            answeredQuestions = emptyMap()
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MsqQuestionDarkViewPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {

        McqQuestionView(
            questions = listOf(
                McqQuestion(
                    question = "What is the capital of France?",
                    answerList = listOf("Paris", "London", "Berlin", "Madrid"),
                ),
                McqQuestion(
                    question = "What is the capital of Germany?",
                    answerList = listOf("Paris", "London", "Berlin", "Madrid"),
                ),
            ),
            currentQuestionIdx = 0,
            onQuestionChange = {},
            onAnswerSelected = {},
            answeredQuestions = emptyMap()
        )
    }
}