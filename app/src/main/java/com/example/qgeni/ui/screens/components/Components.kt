package com.example.qgeni.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qgeni.ui.theme.QGenITheme

/*
    Các thành phần dùng nhiều trong toàn bộ ứng dụng
 */

@Composable
fun NextButton(
    onPrimary: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors =
            if (!onPrimary) {
                IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary
                )
            } else {
                IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.onPrimary
                )
            },
        modifier = Modifier
            .size(60.dp)
            .clip(shape = RoundedCornerShape(size = 100.dp))
            .testTag("next_button")
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "next_button",
            tint =
                if (!onPrimary) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primary
                },
            modifier = Modifier.fillMaxSize()
        )
    }
}
@Composable
fun CustomSolidButton(
    onClick: () -> Unit,
    text: String,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .border(
                width = 5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            ),

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(10.dp)
            )
            .testTag("upload file")
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

@Composable
fun CurvedBackground(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.onPrimary
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .background(MaterialTheme.colorScheme.primary)
            .drawBehind {
                val width = size.width
                val height = size.height

                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, height * 0.6f)
                    cubicTo(
                        width * 0.25f, height * 0.4f,
                        width * 0.75f, height * 0.8f,
                        width, height * 0.6f
                    )
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(
                    path = path,
                    color = color
                )
            }
    )
}

@Preview
@Composable
fun CurvedBackgroundPreview() {
    QGenITheme(dynamicColor = false) {
        CurvedBackground()
    }
}


@Preview
@Composable
fun CustomOutlinedButtonPreview() {
    QGenITheme(dynamicColor =  false) {
        CustomOutlinedButton(
            onClick = {},
            text = "NỘP BÀI",
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun CustomSolidButtonPreview() {
    QGenITheme(dynamicColor =  false) {
        CustomSolidButton(
            onClick = {},
            text = "NỘP BÀI"
        )
    }
}

@Preview
@Composable
fun NextButtonLightPreview() {
    QGenITheme(dynamicColor = false) {
        NextButton(
            onPrimary = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun NextButtonDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        NextButton(
            onPrimary = true,
            onClick = {}
        )
    }
}