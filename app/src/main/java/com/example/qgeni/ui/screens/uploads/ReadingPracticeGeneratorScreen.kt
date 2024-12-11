package com.example.qgeni.ui.screens.uploads

import android.net.Uri
import android.os.Build
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.qgeni.ui.screens.components.CustomOutlinedButton
import com.example.qgeni.ui.screens.components.NextButton
import com.example.qgeni.ui.screens.practices.DeleteBox
import com.example.qgeni.ui.screens.practices.ModeSelectionSwitch
import com.example.qgeni.ui.theme.QGenITheme

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
    viewModel: ReadingPracticeGeneratorViewModel = viewModel(),
) {

    val rpgUIState by viewModel.readingUIState.collectAsState()
    val context = LocalContext.current
    val options = listOf("Model A", "Model B", "Model C")

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
                        { viewModel.updateReadingUploadMode() },
                        enabledText = "Upload",
                        enabledIconResId = R.drawable.file_text,
                        disabledText = "Text",
                        disabledIconResId = R.drawable.eraser
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (rpgUIState.isUploadMode) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (rpgUIState.textUri == Uri.EMPTY) {
                                CustomOutlinedButton(
                                    onClick = {
                                        viewModel.updateReadingUploadFileDialog(
                                            true
                                        )
                                    },
                                    text = "TẢI TỆP",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                DeleteBox(
                                    onDeleteClick = {
                                        viewModel.updateTextUri(context, Uri.EMPTY)
                                    }
                                ) {
                                    CustomOutlinedButton(
                                        onClick = {},
                                        text = rpgUIState.fileName,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Row {
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
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                OutlinedTextField(
                                    value = rpgUIState.inputNumStatement,
                                    onValueChange = {
                                        viewModel.updateReadingInputNumStatement(it)
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
                                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                                            unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                            cursorColor = MaterialTheme.colorScheme.onBackground

                                        ),
                                )
                                Spacer(modifier = Modifier.weight(3f))
                            }
                        }
                    } else {
                        PasteTextField(
                            inputParagraph = rpgUIState.inputParagraph,
                            inputNumStatement = rpgUIState.inputNumStatement,
                            onTextChanged = {
                                viewModel.updateReadingInputParagraph(
                                    it
                                )
                            },
                            onNumStatementChanged = {
                                viewModel.updateReadingInputNumStatement(
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
                        viewModel.createReadingPractice()
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
            viewModel.updateReadingUploadFileDialog(
                false
            )
        }) {
            TextUploadScreen(
                iconId = R.drawable.file_text,
                description = "TXT, up to 50MB",
                color = MaterialTheme.colorScheme.onPrimary,
                onFilePicked = {
                    viewModel.updateTextUri(context, it)
                    viewModel.updateReadingUploadFileDialog(false)
                }
            )
        }
    }

    when (rpgUIState.currentState) {
        is GeneratorState.Loading -> {
            if (viewModel.isFullInfo() != "") {
                MissingFieldDialog(
                    message = viewModel.isFullInfo(),
                    onNextButtonClick = {
                        viewModel.updateReadingGeneratorState(
                            GeneratorState.Idle
                        )
                    },
                    imageResourceId = R.drawable.avatar_3,
                )

            } else {
                LoadingScreen(
                    lottieResourceId = R.raw.fairy,
                    message = "Tiên nữ đang đi tìm nguyên liệu",
                    onStopClicked = {
                        //
                    }
                )
            }

        }

        is GeneratorState.Titling -> {
            SaveScreen(
                title = rpgUIState.title,
                onTitleChange = { viewModel.updateTitle(it) },
                onNextButtonClick = viewModel::saveReadingPractice
            )
        }

        is GeneratorState.Saving -> {
            LoadingScreen(
                lottieResourceId = R.raw.fairy,
                message = "Tiên nữ đang lưu đề",
                onStopClicked = {
                    //
                }
            )
        }

        is GeneratorState.Success -> {
            SuccessScreen(
                onDismissRequest = {
                    viewModel.updateReadingGeneratorState(
                        GeneratorState.Idle
                    )
                },
                onStayButtonClick = {
                    viewModel.updateReadingGeneratorState(
                        GeneratorState.Idle
                    )
                },
                onLeaveButtonClick = {
                    viewModel.updateReadingGeneratorState(GeneratorState.Idle)
                    onLeaveButtonClick()
                },
                imageResourceId = R.drawable.fairy3
            )
        }

        is GeneratorState.Error -> {
            ErrorScreen(
                onDismissRequest = {
                    viewModel.updateReadingGeneratorState(
                        GeneratorState.Idle
                    )
                },
                onLeaveButtonClick = {
                    viewModel.updateReadingGeneratorState(
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
