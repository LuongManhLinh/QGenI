package com.example.qgeni.ui.screens.practices

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qgeni.R
import com.example.qgeni.ui.screens.components.CustomSolidButton
import com.example.qgeni.ui.screens.utils.formatTime
import com.example.qgeni.ui.theme.QGenITheme

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
    timeString: String,
    imageList: List<Bitmap>,
    imageLabelList: List<String>,
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit,
    onSubmitClick: () -> Unit,
) {
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
                text = timeString,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomSolidButton(
                onClick = onSubmitClick,
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

        Row(
            modifier = Modifier.clickable(
                onClick = onPlayClick
            )
        ) {
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(
                        end = 16.dp
                    )
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Outlined.PlayArrow,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "Play",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
        }

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
            items(imageList.size) { index ->
                ImageBox(
                    image = imageList[index],
                    label = imageLabelList[index],
                )
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
    image: Bitmap,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp),
            )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp)
        )
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = label,
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
        val context = LocalContext.current
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar)
        val bitmapList = listOf(bitmap, bitmap, bitmap)
        ImageQuestionView(
            currentQuestion = 0,
            timeString = "00:00",
            imageList = bitmapList,
            imageLabelList = listOf("Pic. A", "Pic. B", "Pic. C"),
            onPlayClick = {},
            onSubmitClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageQuestionDarkViewPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        val context = LocalContext.current
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar)
        val bitmapList = listOf(bitmap, bitmap, bitmap)
        ImageQuestionView(
            currentQuestion = 0,
            timeString = "00:00",
            imageList = bitmapList,
            imageLabelList = listOf("Pic. A", "Pic. B", "Pic. C"),
            onPlayClick = {},
            onSubmitClick = {}
        )
    }
}





