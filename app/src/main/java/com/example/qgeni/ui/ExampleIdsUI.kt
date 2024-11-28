package com.example.qgeni.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier,
    viewModel: ExampleIdsViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            viewModel.updateImageUri(uri)
        }
    }
    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


        val pickFiles = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                viewModel.getFiles(context, uri)
            }
            Log.d("ExampleIdsUI", "uri: $uri")
        }

    pickFiles.launch(arrayOf("application/pdf", "application/msword", "text/plain"))


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(4.dp).padding(top = 12.dp)
    ) {
        Row {
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
//                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                pickFiles.launch(arrayOf("application/pdf", "application/msword", "text/plain"))
            }) {
                Text("Choose image")
            }

            Button(onClick = {
                viewModel.getSimilarImage(context)
            }) {
                Text("Get questions")
            }

            OutlinedTextField(
                value = uiState.numQuestion,
                onValueChange = viewModel::updateNumDesiredImage,
                modifier = Modifier.width(64.dp)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
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
                        .padding(bottom = 36.dp),
                    contentScale = ContentScale.FillWidth
                )
            }

            item {
                Text(
                    text = "Response time: ${uiState.responseTime} ms",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(4.dp)
                )
            }

            items(uiState.responseImgAndDesc.size) {
                val imgAndDesc = uiState.responseImgAndDesc[it]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        (it + 1).toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                    Image(
                        bitmap = imgAndDesc.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 4.dp,
                                end = 4.dp,
                                bottom = 48.dp
                            ),
                        contentScale = ContentScale.FillWidth
                    )

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Prev() {
    ExampleIdsUI(viewModel = ExampleIdsViewModel())
}