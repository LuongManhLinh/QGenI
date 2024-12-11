package com.example.qgeni.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.ui.screens.components.NextButton
import com.example.qgeni.ui.theme.QGenITheme
import com.example.qgeni.utils.ErrorMessages

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
) {
//    var email by remember { mutableStateOf("") }

    val forgotPasswordUIState by forgotPasswordViewModel.forgotPasswordUIState.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
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
                text = "",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        ForgotPasswordPage(
            email = forgotPasswordUIState.email,
            onEmailChange = { forgotPasswordViewModel.updateEmail(it)},
            isEmailError = forgotPasswordUIState.isEmailError,
            onKeyBoardActions = { forgotPasswordViewModel.reset() },
            modifier = Modifier.weight(1f)
        )
        Row {
            Spacer(modifier = Modifier.weight(2f))
            NextButton(
                onPrimary = false,
                onClick = {
                    forgotPasswordViewModel.updateConstraint()
                    if (forgotPasswordViewModel.checkEmpty()) {
                        onNextButtonClick()
                    }
                }
            )
            Spacer(modifier = Modifier.weight(0.25f))
        }
        Spacer(modifier = Modifier.height(56.dp))
    }
}



@Composable
fun ForgotPasswordPage(
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailError: Boolean,
    onKeyBoardActions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 32.dp,
                end = 32.dp
            )
    ) {
        Text(
            text = "Quên mật khẩu",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Nhập email của bạn",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                onEmailChange(it)
                onKeyBoardActions()
            },
            label = {
                Text(
                    text = "Địa chỉ email"
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Email,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            isError = isEmailError,
            trailingIcon = {
                if (isEmailError)
                    Icon(Icons.Outlined.Error,"error", tint = MaterialTheme.colorScheme.error)
            },
            supportingText = {
                if (isEmailError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = ErrorMessages.EmptyField.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            shape = RoundedCornerShape(size = 10.dp),
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
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ForgotPasswordLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ForgotPasswordScreen(
            onBackClick = {},
            onNextButtonClick = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ForgotPasswordDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ForgotPasswordScreen(
            onBackClick = {},
            onNextButtonClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ForgotPasswordPagePreview() {
    QGenITheme(dynamicColor = false) {
        ForgotPasswordPage(
            email = "",
            onEmailChange = {},
            false,
            {}
        )
    }
}