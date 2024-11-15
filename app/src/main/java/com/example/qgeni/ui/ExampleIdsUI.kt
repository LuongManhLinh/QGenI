package com.example.qgeni.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.qgeni.R

@Composable
fun ExampleIdsUI(
    viewModel: ExampleIdsViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.updateImageUri(uri)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(4.dp).padding(top = 12.dp)
    ) {
        Row(
        ) {
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text("Host")
                OutlinedTextField(
                    value = uiState.host,
                    onValueChange = { viewModel.updateHost(it) }
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Port")
                OutlinedTextField(
                    value = uiState.port,
                    onValueChange = { viewModel.updatePort(it) }
                )
            }
        }

        Row {
            Button(onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Text("Choose image")
            }

            Button(onClick = {
                viewModel.getSimilarImage(context)
            }) {
                Text("Get similar")
            }

            OutlinedTextField(
                value = uiState.numDesiredImage,
                onValueChange = viewModel::updateNumDesiredImage,
                modifier = Modifier.width(64.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth().background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(uiState.imageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    error = painterResource(R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    contentScale = ContentScale.Crop
                )
            }

            items(uiState.responseImages.size) {
                val bitmap = uiState.responseImages[it]
                Text((it + 1).toString())
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 12.dp
                        )
                )
            }
        }



    }
}


@Preview(showBackground = true)
@Composable
private fun Prev() {
    ExampleIdsUI(viewModel = ExampleIdsViewModel())
}