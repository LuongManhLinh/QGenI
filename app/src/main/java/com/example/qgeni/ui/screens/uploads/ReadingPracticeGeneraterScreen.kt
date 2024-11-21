package com.example.qgeni.ui.screens.uploads

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.qgeni.R
import com.example.qgeni.ui.screens.components.CustomOutlinedButton
import com.example.qgeni.ui.screens.components.NextButton
import com.example.qgeni.ui.screens.practices.ModeSelectionSwitch
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.delay

/*
    Màn hình tạo đề đọc
 */

@Composable
fun ReadingPracticeGeneratorScreen(
    onBackClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onLeaveButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showUploadFileDialog by remember { mutableStateOf(false) }
    val options = listOf("Model A", "Model B", "Model C")
    var selectedOption by remember { mutableStateOf("Chọn model") }
    var isUploadMode by remember { mutableStateOf(true) }
    var currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Idle) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
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
                        isHighlightMode = isUploadMode,
                        onIsHighlightModeChange = {isUploadMode = it},
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
                    if (isUploadMode) {
                        CustomOutlinedButton(
                            onClick = { showUploadFileDialog = true },
                            text = "TẢI TỆP",
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        PasteTextField(
                        )
                    }
                }

                SelectModelScreen(
                    options = options,
                    selectedOption = selectedOption,
                    onSelectedItemChange = { option ->
                        selectedOption = option
                    },
                )
            }

            Row {
                Spacer(modifier = Modifier.weight(2f))
                NextButton(
                    onPrimary = false,
                    onClick = {
                        currentState = GeneratorState.Loading
                        onNextButtonClick
                    }
                )
                Spacer(modifier = Modifier.weight(0.25f))
            }
            Spacer(modifier = Modifier.height(56.dp))
        }

    }
    if (showUploadFileDialog) {
        Dialog(onDismissRequest = { showUploadFileDialog = false }) {
            UploadFileScreen(
                iconId = R.drawable.file_text,
                description = "TXT, up to 50MB",
                color = MaterialTheme.colorScheme.onPrimary
            ) {}
        }
    }

    when (currentState) {
        is GeneratorState.Loading -> {
            LaunchedEffect(currentState) {
                delay(5000) // 3 giây
                currentState = GeneratorState.Error
            }
            LoadingScreen(
                lottieResourceId = R.raw.fairy,
                message = "Tiên nữ đang đi tìm nguyên liệu"
            )
        }

        is GeneratorState.Success -> {
            SuccessScreen(
                currentState = currentState,
                onDismissRequest = { currentState = GeneratorState.Idle },
                onStayButtonClick = { currentState = GeneratorState.Idle },
                onLeaveButtonClick = {
                    currentState = GeneratorState.Idle
                    onLeaveButtonClick()
                },
                imageResourceId = R.drawable.fairy3
            )
        }

        is GeneratorState.Error -> {
            ErrorScreen(
                currentState = currentState,
                onDismissRequest = { currentState = GeneratorState.Idle },
                onLeaveButtonClick = { currentState = GeneratorState.Idle },
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
    onTextChanged: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onTextChanged(newText)
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
        singleLine = false,
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
}

@Preview
@Composable
fun PasteTextFieldPreview() {
    QGenITheme {
        PasteTextField()
    }
}

@Preview(showSystemUi = true)
@Composable
fun ReadingPracticeGeneratorLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ReadingPracticeGeneratorScreen(
            onBackClick = {},
            onNextButtonClick = {},
            {}
        )
    }
}

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
