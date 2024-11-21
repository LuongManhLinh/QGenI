package com.example.qgeni.ui.screens.practices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qgeni.R
import com.example.qgeni.ui.screens.ItemCard
import com.example.qgeni.ui.theme.QGenITheme

/*
    Màn hình lựa chọn xem danh sách đề đọc hay đề nghe
 */

@Composable
fun SelectionScreen(
    onBackClick: () -> Unit,
    onReadingListClick: () -> Unit,
    onListeningListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                text = "Balo",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.primary
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.backpack1),
                contentDescription = "backpack1"
            )
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                ItemCard(
                    imageId = R.drawable.backpack2,
                    title = "Đề bài đọc",
                    onClick = onReadingListClick
                )
                Spacer(modifier = Modifier.height(32.dp))
                ItemCard(
                    imageId = R.drawable.backpack1,
                    title = "Đề bài nghe",
                    onClick = onListeningListClick
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun SelectionLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        SelectionScreen(
            onBackClick = {},
            onListeningListClick = {},
            onReadingListClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SelectionDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        SelectionScreen(
            onBackClick = {},
            onListeningListClick = {},
            onReadingListClick = {}
        )
    }
}
