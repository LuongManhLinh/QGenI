package com.example.qgeni.ui.screens.uploads

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.R
import com.example.qgeni.data.model.McqQuestion
import com.example.qgeni.data.model.MockReadingPracticeItem
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.ui.screens.components.CustomOutlinedButton
import com.example.qgeni.ui.screens.components.NextButton
import com.example.qgeni.ui.screens.practices.ModeSelectionSwitch
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.delay
import java.time.LocalDate

/*
    Màn hình tạo đề đọc
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReadingPracticeGeneratorScreen(
    onBackClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onLeaveButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    basePracticeGeneratorViewModel: BasePracticeGeneratorViewModel = viewModel(),
) {

    val rpgUIState by basePracticeGeneratorViewModel.readingUIState.collectAsState()
    val context = LocalContext.current
//    var showUploadFileDialog by remember { mutableStateOf(false) }
    val options = listOf("Model A", "Model B", "Model C")
//    var selectedOption by remember { mutableStateOf("Chọn model") }
//    var isUploadMode by remember { mutableStateOf(true) }
//    var currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Idle) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.onPrimary
            )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth()
                .background(
                    color = Color.Transparent
                ),
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
                text = "Tạo đề đọc",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Column(
            modifier = modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.reading),
                    contentDescription = "reading"
                )
            }

            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 0.dp
                    )
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    ModeSelectionSwitch(
                        isHighlightMode = rpgUIState.isUploadMode,
                        onIsHighlightModeChange =
//                        {isUploadMode = it},
                        { basePracticeGeneratorViewModel.updateReadingUploadMode() },
                        enabledText = "Upload",
                        enabledIconResId = R.drawable.file_text,
                        disabledText = "Text",
                        disabledIconResId = R.drawable.eraser
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (rpgUIState.isUploadMode) {
                        Column {
                            if (rpgUIState.textUri == Uri.EMPTY) {
                                CustomOutlinedButton(
//                            onClick = { showUploadFileDialog = true },
                                    onClick = {
                                        basePracticeGeneratorViewModel.updateReadingUploadFileDialog(
                                            true
                                        )
                                    },
                                    text = "TẢI TỆP",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Column {
                                    CustomOutlinedButton(
                                        onClick = {},
                                        text = rpgUIState.fileName,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Spacer(Modifier.height(75.dp))
                            OutlinedTextField(
                                value = rpgUIState.inputNumStatement,
                                onValueChange = {
                                    basePracticeGeneratorViewModel.updateReadingInputNumStatement(it)
                                }
                            )
                        }
                    } else {
                        PasteTextField(
                            inputParagraph = rpgUIState.inputParagraph,
                            inputNumStatement = rpgUIState.inputNumStatement,
                            onTextChanged = {
                                basePracticeGeneratorViewModel.updateReadingInputParagraph(
                                    it
                                )
                            },
                            onNumStatementChanged = {
                                basePracticeGeneratorViewModel.updateReadingInputNumStatement(
                                    it
                                )
                            }
                        )
                    }
                }
            }
            Row {
                Spacer(modifier = Modifier.weight(2f))
                NextButton(
                    onPrimary = false,
                    onClick = {
                        basePracticeGeneratorViewModel.updateReadingGeneratorState(GeneratorState.Loading)
                        onNextButtonClick()
                    }
                )
                Spacer(modifier = Modifier.weight(0.25f))
            }
            Spacer(modifier = Modifier.height(56.dp))
        }

    }
    if (rpgUIState.showUploadFileDialog) {
        Dialog(onDismissRequest = {
            basePracticeGeneratorViewModel.updateReadingUploadFileDialog(
                false
            )
        }) {
            UploadFileScreen(
                iconId = R.drawable.file_text,
                description = "TXT, up to 50MB",
                color = MaterialTheme.colorScheme.onPrimary,
                onFilePicked = {
                    basePracticeGeneratorViewModel.updateTextUri(context, it)
                    basePracticeGeneratorViewModel.updateReadingUploadFileDialog(false)
                }
            )
        }
    }
    when (rpgUIState.currentState) {
        is GeneratorState.Loading -> {
            LaunchedEffect(rpgUIState.currentState) {
                Log.i("amount", 11.toString())
                val passage: String
                val readingQuestions: List<McqQuestion>
                if (rpgUIState.isUploadMode) {
                    passage = rpgUIState.fileContent
                    readingQuestions =
                        basePracticeGeneratorViewModel.fetchReadingQuestions(rpgUIState.fileContent)
                } else {
                    passage = rpgUIState.inputParagraph
                    readingQuestions =
                        basePracticeGeneratorViewModel.fetchReadingQuestions(rpgUIState.inputParagraph)
                }
                if (readingQuestions.isNotEmpty()) {
                    Log.i("ReadingQuestion", readingQuestions.size.toString())
                    val id = MockReadingPracticeItem.readingPracticeItemList.size
                    MockReadingPracticeItem.readingPracticeItemList.add(
                        ReadingPracticeItem(
                            id = id,
                            title = "Bài đọc ${id + 1}",
                            passage = passage,
                            numStatement = rpgUIState.inputNumStatement,
                            creationDate = LocalDate.now(),
                            isNew = id < 3,
                            questionList = readingQuestions
                        )
                    )
                    basePracticeGeneratorViewModel.updateReadingGeneratorState(GeneratorState.Success)
                } else
                    basePracticeGeneratorViewModel.updateReadingGeneratorState(GeneratorState.Error)
            }
            LoadingScreen(
                lottieResourceId = R.raw.fairy,
                message = "Tiên nữ đang đi tìm nguyên liệu"
            )
        }

        is GeneratorState.Success -> {
            if (!rpgUIState.isGenerateSuccess) {
                Log.i("Title", rpgUIState.title)
                SaveScreen(
                    title = rpgUIState.title,
                    onTitleChange = { basePracticeGeneratorViewModel.updateTitle(it) },
                    currentState = rpgUIState.currentState,
                    onNextButtonClick = {
                        basePracticeGeneratorViewModel.updateGenerateSuccess(true)
                        val id = MockReadingPracticeItem.readingPracticeItemList.size - 1
                        if (id >= 0) {
                            MockReadingPracticeItem.readingPracticeItemList[id] =
                                MockReadingPracticeItem.readingPracticeItemList[id].copy(title = rpgUIState.title)
                        }
                    },
                )
            } else {
                SuccessScreen(
                    currentState = rpgUIState.currentState,
                    onDismissRequest = {
                        basePracticeGeneratorViewModel.updateReadingGeneratorState(
                            GeneratorState.Idle
                        )
                        basePracticeGeneratorViewModel.updateGenerateSuccess(false)
                    },
                    onStayButtonClick = {
                        basePracticeGeneratorViewModel.updateReadingGeneratorState(
                            GeneratorState.Idle
                        )
                        basePracticeGeneratorViewModel.updateGenerateSuccess(false)
                    },
                    onLeaveButtonClick = {
                        basePracticeGeneratorViewModel.updateReadingGeneratorState(GeneratorState.Idle)
                        basePracticeGeneratorViewModel.updateGenerateSuccess(false)
                        onLeaveButtonClick()
                    },
                    imageResourceId = R.drawable.fairy3
                )
            }
        }

        is GeneratorState.Error -> {
            ErrorScreen(
                currentState = rpgUIState.currentState,
                onDismissRequest = {
                    basePracticeGeneratorViewModel.updateReadingGeneratorState(
                        GeneratorState.Idle
                    )
                },
                onLeaveButtonClick = {
                    basePracticeGeneratorViewModel.updateReadingGeneratorState(
                        GeneratorState.Idle
                    )
                },

                imageResourceId = R.drawable.fairy_sorry,
                message = "Tiên nữ học việc của chúng tôi mắc lỗi nào đó, thử lại hoặc chọn tiên nữ khác"
            )
        }

        else -> Unit // Không hiển thị gì khi Idle
    }
}

@Composable
fun PasteTextField(
    modifier: Modifier = Modifier,
    inputParagraph: String,
    inputNumStatement: String,
    onTextChanged: (String) -> Unit,
    onNumStatementChanged: (String) -> Unit,
) {
//    var text by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = inputParagraph,
            onValueChange = {
                onTextChanged(it)
            },
            placeholder = {
                Text(
                    text = "Dán đoạn văn của bạn vào đây",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            },
            modifier = modifier
                .fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(size = 10.dp),
            maxLines = Int.MAX_VALUE,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Default
            ),
            colors = OutlinedTextFieldDefaults
                .colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                text = "Số câu hỏi",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            OutlinedTextField(
                value = inputNumStatement,
                onValueChange = {
                    onNumStatementChanged(it)
                },
                placeholder = {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                },
                modifier = modifier
                    .weight(1f),
                singleLine = false,
                shape = RoundedCornerShape(size = 10.dp),
                maxLines = Int.MAX_VALUE,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Default,
                    keyboardType = KeyboardType.Number // Bàn phím số
                ),
                colors = OutlinedTextFieldDefaults
                    .colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
            )
            Spacer(modifier = Modifier.weight(3f))
        }
    }
}

@Preview
@Composable
fun PasteTextFieldPreview() {
    QGenITheme {
        PasteTextField(
            inputParagraph = "",
            inputNumStatement = "",
            onTextChanged = {},
            onNumStatementChanged = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun ReadingPracticeGeneratorLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ReadingPracticeGeneratorScreen(
            onBackClick = {},
            onNextButtonClick = {},
            onLeaveButtonClick = {},
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true)
@Composable
fun ReadingPracticeGeneratorDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ReadingPracticeGeneratorScreen(
            onBackClick = {},
            onNextButtonClick = {},
            {}
        )
    }
}
