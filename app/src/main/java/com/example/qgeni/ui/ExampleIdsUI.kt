package com.example.qgeni.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Log.e("URI", uri.toString())
        Log.e("URI PATH", uri?.path ?: "Path is null")
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

        Button(onClick = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Text("Click to choose image")
        }

        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(uiState.imageUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            error = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier
                .widthIn(min = 300.dp)
                .heightIn(max = 400.dp, min = 200.dp),
            contentScale = ContentScale.Crop
        )

        Button(
            onClick = {
                viewModel.describeImage(context)
            }
        ) {
            Text("Describe")
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Cyan
            )
        ) {
            LazyColumn {
                item {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Local Server: ")
                            }
                            append(uiState.serverResponse)
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Gemini: ")
                            }
                            append(uiState.geminiResponse)
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
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