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
import com.example.qgeni.data.model.MockReadingPracticeItem
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.ui.theme.QGenITheme

/*
    Màn hình thực hiện đề đọc
    gồm PassageView và TrueFalseQuestionView
 */

@Composable
fun ReadingPracticeScreen(
    readingPracticeItem: ReadingPracticeItem,
    onBackClick: () -> Unit,
    readingPracticeViewModel: ReadingPracticeViewModel = viewModel()
) {
//    val answeredQuestions = remember { mutableStateMapOf<Int, String>() }

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
        PassageView(
            text = readingPracticeItem.passage,
            modifier = Modifier.weight(1f),
            viewModel = readingPracticeViewModel
        )
        TrueFalseQuestionView(
            questions = readingPracticeItem.questionList,
//            answeredQuestions = answeredQuestions,
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

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ReadingPracticeLightScreenPreview() {
//    QGenITheme(dynamicColor = false) {
//        ReadingPracticeScreen(
//            MockReadingPracticeItem.readingPracticeItem,
//            {}
//        )
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ReadingPracticeDarkScreenPreview() {
//    QGenITheme(dynamicColor = false, darkTheme = true) {
//        ReadingPracticeScreen(
//            MockReadingPracticeItem.readingPracticeItem,
//            {}
//        )
//    }
//}
