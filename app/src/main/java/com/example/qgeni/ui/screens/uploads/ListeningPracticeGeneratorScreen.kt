package com.example.qgeni.ui.screens.uploads

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.R
import com.example.qgeni.ui.screens.components.CustomOutlinedButton
import com.example.qgeni.ui.screens.components.NextButton
import com.example.qgeni.ui.theme.QGenITheme

/*
    Màn hình tạo đề nghe
 */

@Composable
fun ListeningPracticeGeneratorScreen(
    onBackClick: () -> Unit,
    onLeaveButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListeningPracticeGeneratorViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

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
                text = "Tạo đề nghe",
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
                    painter = painterResource(R.drawable.listening),
                    contentDescription = "listening"
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
                if (uiState.imageList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomOutlinedButton(
                            onClick = {
                                viewModel.updateUploadFileDialogVisibility(true)
                            },
                            text = "TẢI TỆP",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    LazyRow {
                        items(uiState.imageList) {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(10.dp),
                                    )
                                    .heightIn(max = 80.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(10.dp),
                                    ),

                                )
                        }
                    }

                }

                Spacer(Modifier.padding(vertical = 16.dp))
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

            }
            Row {
                Spacer(modifier = Modifier.weight(2f))
                NextButton(
                    onPrimary = false,
                    onClick = {
                        viewModel.createListeningPractice()
                    }
                )
                Spacer(modifier = Modifier.weight(0.25f))
            }
            Spacer(modifier = Modifier.height(56.dp))
        }

    }

    
    if (uiState.showUploadFileDialog) {
        Dialog(
            onDismissRequest = { viewModel.updateUploadFileDialogVisibility(false) }
        ) {
            ImageUploadScreen(
                iconId = R.drawable.file_text,
                description = "JPEG, PNG, up to 50MB",
                color = MaterialTheme.colorScheme.onPrimary,
                onImagePicked = viewModel::updateSelectedImage
            )

        }
    }

    when (uiState.currentState) {
        is GeneratorState.Loading -> {
            LoadingScreen(
                lottieResourceId = R.raw.young_genie,
                message = "Thần đèn đang đi tìm nguyên liệu"
            )
        }

        is GeneratorState.Titling -> {
            SaveScreen(
                title = uiState.practiceTitle,
                onTitleChange = {
                    viewModel.updatePracticeTitle(it)
                },
                onNextButtonClick = viewModel::saveListeningPracticeTitle
            )
        }

        is GeneratorState.Saving -> {
            LoadingScreen(
                lottieResourceId = R.raw.young_genie,
                message = "Thần đèn đang lưu đề"
            )
        }

        is GeneratorState.Success -> {
            SuccessScreen(
                onDismissRequest = {
                    viewModel.reset()
                },
                onStayButtonClick = {
                    viewModel.reset()
                },
                onLeaveButtonClick = {
                    viewModel.updateUploadFileDialogVisibility(false)
                    onLeaveButtonClick()
                },
                imageResourceId = R.drawable.oldman_and_girl
            )
        }

        is GeneratorState.Error -> {
            ErrorScreen(
                onDismissRequest = {
                    viewModel.updateCurrentState(GeneratorState.Idle)
                },
                onLeaveButtonClick = {
                    viewModel.updateCurrentState(GeneratorState.Idle)
                },
                imageResourceId = R.drawable.genie_sorry,
                message = "Thần đèn học việc của chúng tôi mắc lỗi nào đó, thử lại hoặc chọn thần đèn khác"
            )
        }

        else -> Unit // Không hiển thị gì khi Idle
    }
}


@Preview(showSystemUi = true)
@Composable
fun ListeningPracticeGeneratorLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ListeningPracticeGeneratorScreen(
            onBackClick = {},
            {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListeningPracticeGeneratorDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ListeningPracticeGeneratorScreen(
            onBackClick = {},
            {}
        )
    }
}
