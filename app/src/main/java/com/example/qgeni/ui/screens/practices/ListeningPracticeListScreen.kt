package com.example.qgeni.ui.screens.practices
import android.os.Build
import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.R
import com.example.qgeni.ui.theme.QGenITheme

/*
    Hiển thị danh sách đề nghe
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListeningPracticeListScreen(
    onBackClick: () -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListeningPracticeListViewModel = viewModel()
) {

    val uiState by viewModel.practiceListUIState.collectAsState()
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
                text = "Ngăn đề nghe",
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
                items(items =uiState.practiceItemList, key = { item -> item.id}) { item ->
                    PracticeItemCard(
                        practiceItem = item,
                        onDeleteClick = {
                            viewModel.selectItem(item.id)
                            viewModel.toggleDeleteDialog(true)
                        },
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    Log.i("check click", "aa")
                                    viewModel.selectItem(item.id)
                                    viewModel.toggleOpenDialog(true)
                                }
                            ).testTag("open dialog")
                    )
                }
            }
        }
    }

    if(uiState.showDeleteDialog) {
        DeleteConfirmDialog(
            onDismissRequest = {viewModel.toggleDeleteDialog(false)},
            onDeleteClick = {viewModel.toggleDeleteDialog(false)},
            imageResourceId = R.drawable.listening_open_delete_confirm
        )
    }
    if(uiState.showOpenDialog) {
        OpenConfirmDialog(
            onDismissRequest = {viewModel.toggleOpenDialog(false)},
            onOpenClick = {
                viewModel.toggleOpenDialog(false)
                onItemClick(uiState.selectedItemId!!.toHexString())
            },
            imageResourceId = R.drawable.listening_open_delete_confirm
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningPracticeListLightScreenPreview() {
    QGenITheme(dynamicColor = false) {
        ListeningPracticeListScreen(
            onBackClick = {},
            onItemClick = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListeningPracticeListDarkScreenPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ListeningPracticeListScreen(
            onBackClick = {},
            onItemClick = {}
        )
    }
}

