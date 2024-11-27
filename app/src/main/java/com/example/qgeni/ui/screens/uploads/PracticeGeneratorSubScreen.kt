package com.example.qgeni.ui.screens.uploads

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.qgeni.R
import com.example.qgeni.ui.theme.QGenITheme

/*
    Các thành phần dùng chung trong Generator
 */

sealed class GeneratorState {
    data object Idle : GeneratorState() // Lúc bình thường
    data object Loading : GeneratorState() // Đang tạo đề
    data object Success : GeneratorState() // Tạo xong
    data object Error : GeneratorState() // Bị lỗi
}

@Composable
fun LoadingScreen(
    @RawRes
    lottieResourceId: Int = R.raw.fairy,
    message: String,
) {
    Dialog(onDismissRequest = {}) { // Loading không để dismiss
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LottieAnimation(
                    composition = rememberLottieComposition(LottieCompositionSpec.RawRes(lottieResourceId)).value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Bạn chờ chút nhé",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun SuccessScreen(
    currentState: GeneratorState,
    onDismissRequest: () -> Unit,
    onStayButtonClick: () -> Unit,
    onLeaveButtonClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int = R.drawable.fairy3,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "TẠO ĐỀ THÀNH CÔNG",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = onStayButtonClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "Ở LẠI",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = onLeaveButtonClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "ĐẾN KHO ĐỀ",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SaveScreen(
    currentState: GeneratorState,
    onNextButtonClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int = R.drawable.fairy3,
) {

    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        placeholder = {
                            Text(
                                text = "Nhập tên đề",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = false,
                        shape = RoundedCornerShape(size = 10.dp),
                        maxLines = Int.MAX_VALUE,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Default,
                            keyboardType = KeyboardType.Number // Bàn phím số
                        ),
                        colors = OutlinedTextFieldDefaults
                            .colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                            ),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {

                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = onNextButtonClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "Xác nhận",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ErrorScreen(
    currentState: GeneratorState,
    onDismissRequest: () -> Unit,
    onLeaveButtonClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int = R.drawable.fairy_sorry,
    message: String,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = onLeaveButtonClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "THỬ LẠI",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun SaveScreenLightPreview() {
    QGenITheme(dynamicColor = false) {
        val currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Loading) }
        SaveScreen(
            currentState = currentState,
            {},
            imageResourceId = R.drawable.avatar_3,
        )
    }
}

@Preview
@Composable
fun SaveScreenDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        val currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Loading) }
        SaveScreen(
            currentState = currentState,
            {},
            imageResourceId = R.drawable.savescreengenie,
        )
    }
}

@Preview
@Composable
fun ErrorLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        var currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Loading) }
        ErrorScreen(
            currentState = currentState,
            {},
            {},
            imageResourceId = R.drawable.fairy_sorry,
            message = "Tiên nữ học việc của chúng tôi mắc lỗi nào đó, thử lại hoặc chọn tiên nữ khác"
        )
    }
}

@Preview
@Composable
fun ErrorDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        var currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Loading) }
        ErrorScreen(
            currentState = currentState,
            {},
            {},
            imageResourceId = R.drawable.fairy_sorry,
            message = "Tiên nữ học việc của chúng tôi mắc lỗi nào đó, thử lại hoặc chọn tiên nữ khác"
        )
    }
}

@Preview
@Composable
fun LoadingLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        LoadingScreen(
            message = "Tiên nữ đang đi tìm nguyên liệu"
        )
    }
}

@Preview
@Composable
fun LoadingDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        LoadingScreen(
            message = "Tiên nữ đang đi tìm nguyên liệu"
        )
    }
}

@Preview
@Composable
fun SuccessLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        var currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Loading) }
        SuccessScreen(
            currentState = currentState,
            {},
            {},
            {},
            imageResourceId = R.drawable.fairy3
        )
    }
}

@Preview
@Composable
fun SuccessDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        var currentState by remember { mutableStateOf<GeneratorState>(GeneratorState.Loading) }
        SuccessScreen(
            currentState = currentState,
            {},
            {},
            {},
            imageResourceId = R.drawable.fairy3
        )
    }
}