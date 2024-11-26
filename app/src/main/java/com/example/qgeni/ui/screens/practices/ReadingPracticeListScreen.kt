package com.example.qgeni.ui.screens.practices

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.R
import com.example.qgeni.data.model.MockReadingPracticeItem
import com.example.qgeni.data.model.ReadingPracticeItem
import com.example.qgeni.ui.theme.QGenITheme

/*
    Màn hình hiển thị danh sách đề đọc
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReadingPracticeListScreen(
    readingPracticeItemList: List<ReadingPracticeItem>,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    readingPracticeListViewModel: ReadingPracticeListViewModel = viewModel()
) {

    val rplUIState by readingPracticeListViewModel.practiceListUIState.collectAsState()

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
                text = "Ngăn đề đọc",
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
                    painter = painterResource(R.drawable.backpack2),
                    contentDescription = "backpack1"
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .background(color = MaterialTheme.colorScheme.onPrimary),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = readingPracticeItemList, key = {item -> item.id}) { item ->
                    PracticeItemCard(
                        practiceItem = item,
                        onDeleteClick = {
                            readingPracticeListViewModel.selectItem(item.id)
                            readingPracticeListViewModel.toggleDeleteDialog(true)
                        },
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    readingPracticeListViewModel.selectItem(item.id)
                                    readingPracticeListViewModel.toggleOpenDialog(true)
                                }
                            )
                    )
                }
            }
        }
    }
    if (rplUIState.showDeleteDialog) {
        DeleteConfirmDialog(
            onDismissRequest = {readingPracticeListViewModel.toggleDeleteDialog(false)},
            onDeleteClick = {readingPracticeListViewModel.toggleDeleteDialog(false)}
        )
    }
    if (rplUIState.showOpenDialog) {
        OpenConfirmDialog(
            onDismissRequest = {readingPracticeListViewModel.toggleOpenDialog(false)},
            onOpenClick = {
                readingPracticeListViewModel.toggleOpenDialog(false)
                onItemClick(rplUIState.selectedItemId)
            }
        )
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReadingPracticeListLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ReadingPracticeListScreen(
            readingPracticeItemList =
            MockReadingPracticeItem.readingPracticeItemList,
            onBackClick = {},
            onDeleteClick = {},
            onItemClick = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReadingPracticeListDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ReadingPracticeListScreen(
            readingPracticeItemList =
            MockReadingPracticeItem.readingPracticeItemList,
            onBackClick = {},
            onDeleteClick = {},
            onItemClick = {}
        )
    }
}

