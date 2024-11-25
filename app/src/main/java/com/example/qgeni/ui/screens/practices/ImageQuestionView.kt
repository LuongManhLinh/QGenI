package com.example.qgeni.ui.screens.practices

import android.media.AudioRecord
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.data.model.ImageData
import com.example.qgeni.data.model.ImageItem
import com.example.qgeni.ui.screens.components.CustomSolidButton
import com.example.qgeni.ui.screens.utils.formatTime
import com.example.qgeni.ui.theme.QGenITheme
import kotlinx.coroutines.delay

/*
    Phần hiển thị AudioPlayer, lựa chọn ảnh,
    và thời gian cho ListeningPracticeScren
 */

sealed class PlaybackState {
    data object Paused : PlaybackState()
    data object Playing : PlaybackState()
    data object Finished : PlaybackState()
}

@Composable
fun ImageQuestionView(
    currentQuestion: Int,
    record: AudioRecord?,
    imageList: List<ImageItem>,
    modifier: Modifier = Modifier,
    viewModel: ListeningPracticeViewModel
) {
//    var time by remember { mutableLongStateOf(0L) }
//
//    var playbackState by remember { mutableStateOf<PlaybackState>(PlaybackState.Paused) }
//    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val ImqUIState by viewModel.listeningPracticeUIState.collectAsState()
    val duration = 100f

    // Chạy bộ đếm khi trạng thái là Playing
    LaunchedEffect(ImqUIState.playbackState) {
        if (ImqUIState.playbackState is PlaybackState.Playing) {
            while (ImqUIState.sliderPosition < duration) {
//                sliderPosition += 1f
                viewModel.updateSliderPosition(ImqUIState.sliderPosition + 1f)
                delay(1000)
            }
            if (ImqUIState.sliderPosition >= duration) {
//                    playbackState = PlaybackState.Finished
                viewModel.updatePlaybackState(PlaybackState.Finished)
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
//            time += 1000L
            viewModel.updateTime()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary
            )
            .padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Thời gian làm bài: ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = formatTime(ImqUIState.time),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomSolidButton(
                onClick = { /* Submit action */ },
                text = "NỘP BÀI",
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                text = "Record ${currentQuestion + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AudioPlayer(
            playbackState = ImqUIState.playbackState,
            sliderPosition = ImqUIState.sliderPosition,
            duration = duration,
            onSliderPositionChange = { viewModel.updateSliderPosition(it) },
            onPlayClick = {
//                playbackState = when (playbackState) {
//                    is PlaybackState.Paused -> PlaybackState.Playing
//                    is PlaybackState.Playing -> PlaybackState.Paused
//                    is PlaybackState.Finished -> {
//                        sliderPosition = 0f
//                        PlaybackState.Playing
//                    }
//                }
                when (ImqUIState.playbackState) {
                    PlaybackState.Paused -> viewModel.updatePlaybackState(PlaybackState.Playing)
                    PlaybackState.Playing -> viewModel.updatePlaybackState(PlaybackState.Paused)
                    else -> {
                        viewModel.updateSliderPosition(0f)
                        viewModel.updatePlaybackState(PlaybackState.Playing)
                    }
                }
            },
            onValueChangeFinished = {
                if (ImqUIState.sliderPosition >= duration) {
//                    playbackState =
//                        PlaybackState.Finished
                    viewModel.updatePlaybackState(PlaybackState.Finished)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(16.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imageList) { item ->
                ImageBox(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayer(
    playbackState: PlaybackState,
    sliderPosition: Float,
    duration: Float,
    onSliderPositionChange: (Float) -> Unit,
    onPlayClick: () -> Unit,
    onValueChangeFinished: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp),
            )
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPlayClick
        ) {
            Icon(
                imageVector = when (playbackState) {
                    is PlaybackState.Paused -> Icons.Outlined.PlayArrow
                    is PlaybackState.Playing -> Icons.Outlined.Pause
                    is PlaybackState.Finished -> Icons.Outlined.Replay
                },
                contentDescription = "Play/Pause",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = formatTime(sliderPosition.toInt()),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = " / " + formatTime(duration.toInt()),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(2.dp))
        Slider(
            value = sliderPosition,
            onValueChange = onSliderPositionChange,
            valueRange = 0f..duration,
            onValueChangeFinished = onValueChangeFinished,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.tertiary
            ),
            thumb = {},
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {}) {
            Icon(
                Icons.AutoMirrored.Outlined.VolumeUp,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(
            onClick = {},
        ) {
            Icon(
                Icons.Outlined.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ImageBox(
    item: ImageItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp),
            )
    ) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp)
        )
        Image(
            painter = painterResource(item.imageRes),
            contentDescription = item.label,
            contentScale = ContentScale.Fit,
            modifier = Modifier.clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                )
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageQuestionLightViewPreview() {
    QGenITheme(dynamicColor = false) {
        ImageQuestionView(
            currentQuestion = 0,
            null,
            imageList = ImageData.imageList,
            viewModel = viewModel()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageQuestionDarkViewPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ImageQuestionView(
            currentQuestion = 0,
            null,
            imageList = ImageData.imageList,
            viewModel = viewModel()
        )
    }
}

@Preview
@Composable
fun ImageBoxLightPreview() {
    QGenITheme(dynamicColor = false) {
        ImageBox(ImageData.imageList[0])
    }
}

@Preview
@Composable
fun ImageBoxDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        ImageBox(ImageData.imageList[0])
    }
}



