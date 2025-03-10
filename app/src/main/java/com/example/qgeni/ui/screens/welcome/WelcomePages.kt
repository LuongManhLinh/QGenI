package com.example.qgeni.ui.screens.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.qgeni.R
import com.example.qgeni.ui.theme.QGenITheme


data class Item(
    @DrawableRes
    val imageId: Int,
    val quote: String,
    val description: String
)

object Items {
    fun getTabList(): List<Item> {
        return listOf(
            Item(
                imageId = R.drawable.fairy_with_a_girl,
                quote = "Adventure is out there",
                description = "Cute genie"
            ),
            Item(
                imageId = R.drawable.oldman_reading,
                quote = "Adventure is out there",
                description = "Cute genie"
            ),
            Item(
                imageId = R.drawable.fairy3,
                quote = "Adventure is out there",
                description = "Cute genie"
            ),
        )
    }
}


@Composable
fun PageScreen(
    @DrawableRes
    imageId: Int,
    quote: String,
    description: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "old_man_reading",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = quote,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.weight(1.5f))
    }
}


@Composable
fun PageChanger(
    modifier: Modifier = Modifier,
    tabItems: List<Item> = Items.getTabList(),
    time: Long,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        val pagerState = rememberPagerState(initialPage = 0) { tabItems.size }
        var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }
        var timeRemaining by remember { mutableStateOf(time) }

        // Cập nhật lastInteractionTime khi người dùng vuốt
        LaunchedEffect(Unit) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                selectedTabIndex = page
                lastInteractionTime = System.currentTimeMillis()
                timeRemaining = time
            }
        }

        // Tự động chuyển trang khi hết thời gian
        LaunchedEffect(lastInteractionTime) {
            while (true) {
                val currentTime = System.currentTimeMillis()
                timeRemaining = time - (currentTime - lastInteractionTime)

                if (timeRemaining <= 0) {
                    // Tính toán trang kế tiếp
                    val nextPage = (selectedTabIndex + 1) % tabItems.size
                    pagerState.scrollToPage(nextPage)
                    lastInteractionTime = System.currentTimeMillis()
                    timeRemaining = time
                }
                kotlinx.coroutines.delay(100)
            }
        }

        // HorizontalPager với phát hiện thao tác vuốt
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            lastInteractionTime = System.currentTimeMillis()
                            timeRemaining = time
                        },
                        onDragEnd = {
                            lastInteractionTime = System.currentTimeMillis()
                            timeRemaining = time
                        }
                    ) { change, _ ->
                        change.consume()
                    }
                }
        ) { page ->
            val currentPage = page % tabItems.size
            PageScreen(
                tabItems[currentPage].imageId,
                tabItems[currentPage].quote,
                tabItems[currentPage].description
            )
        }
    }
}


@Composable
fun PortDialog(
    onNextButtonClick: () -> Unit,
    host: String,
    onHostChange: (String) -> Unit,
    portDB: String,
    onPortDBChange: (String) -> Unit,
    portImage: String,
    onPortImageChange: (String) -> Unit,
    portCtrl: String,
    onPortCtrlChange: (String) -> Unit,
    @DrawableRes
    imageResourceId: Int = R.drawable.avatar_1,
) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .height(300.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "NHẬP HOST VÀ CÁC PORT MÀ SERVER ĐƯỢC CÀI ĐẶT",
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Justify
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = host,
                        onValueChange = onHostChange,
                        label = {
                            Text(
                                text = "Host",
                            )
                        },
                        shape = RoundedCornerShape(size = 10.dp),
                        colors = OutlinedTextFieldDefaults
                            .colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                cursorColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = portDB,
                        onValueChange = onPortDBChange,
                        label = {
                            Text(
                                text = "Database Port",
                            )
                        },
                        shape = RoundedCornerShape(size = 10.dp),
                        colors = OutlinedTextFieldDefaults
                            .colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                cursorColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = portImage,
                        onValueChange = onPortImageChange,
                        label = {
                            Text(
                                text = "Host Generator Port",
                            )
                        },
                        shape = RoundedCornerShape(size = 10.dp),
                        colors = OutlinedTextFieldDefaults
                            .colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                cursorColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = portCtrl,
                        onValueChange = onPortCtrlChange,
                        label = {
                            Text(
                                text = "Host Control Port",
                            )
                        },
                        shape = RoundedCornerShape(size = 10.dp),
                        colors = OutlinedTextFieldDefaults
                            .colors(
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                cursorColor = MaterialTheme.colorScheme.onPrimary
                            ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {

                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = onNextButtonClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .testTag("Xác nhận")
                        ) {
                            Text(
                                text = "XÁC NHẬN",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PortDialogLightPreview() {
    QGenITheme(dynamicColor = false) {
        PortDialog(
            host = "",
            onHostChange = {},
            portDB = "",
            onPortDBChange = {},
            portImage = "",
            onPortImageChange = {},
            portCtrl = "123",
            onPortCtrlChange = {},
            onNextButtonClick = {}
        )
    }
}

@Preview
@Composable
fun PortDialogDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        PortDialog(
            host = "",
            onHostChange = {},
            portDB = "",
            onPortDBChange = {},
            portImage = "",
            onPortImageChange = {},
            portCtrl = "123",
            onPortCtrlChange = {},
            onNextButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PageChangerPreview() {
    QGenITheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PageChanger(Modifier, Items.getTabList(), 3000L)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PageScreenLightPreview() {
    QGenITheme(dynamicColor = false) {
        PageScreen(
            R.drawable.fairy_with_a_girl,
            "Adventure is out there",
            "Cute genie"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PageScreenDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        PageScreen(
            R.drawable.fairy_with_a_girl,
            "Adventure is out there",
            "Cute genie"
        )
    }
}