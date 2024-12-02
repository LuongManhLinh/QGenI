package com.example.qgeni.ui.screens.practices

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.qgeni.R
import com.example.qgeni.data.model.ListeningPracticeItem
import com.example.qgeni.data.model.PracticeItem
import com.example.qgeni.ui.screens.components.CustomOutlinedButton
import com.example.qgeni.ui.screens.utils.formatDate
import com.example.qgeni.ui.theme.QGenITheme
import org.bson.types.ObjectId
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PracticeItemCard(
    practiceItem: PracticeItem,
    onDeleteClick: () -> Unit,
    @DrawableRes
    newIconResId: Int = R.drawable.resource_new,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = practiceItem.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ngày tạo: ${formatDate(practiceItem.creationDate)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

        }
        if (practiceItem.isNew) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = (-12).dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHighest,
                        RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = newIconResId),
                    contentDescription = "New Icon",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun DisplayScore(
    message: String = "10/10",
    onNextButtonClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int = R.drawable.listening_submit_confirm,
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
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
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
                        ) {
                            Text(
                                text = "VỀ KHO ĐỀ",
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

@Composable
fun DeleteBox(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .heightIn(max = 80.dp)
            .wrapContentSize()
    ) {
        content()
        Icon(
            imageVector = Icons.Default.Cancel,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(2.dp)
                .clickable(
                    onClick = onDeleteClick
                )
        )
    }
}


//reading thì R.drawable.reading_submit_confirm
//leading thì R.drawable.listening_submit_confirm
@Composable
fun SubmitConfirm(
    onDismissRequest: () -> Unit,
    onSubmitClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Bạn có chắc chắn muốn nộp bài không",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = onDismissRequest,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "HỦY",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        CustomOutlinedButton(
                            onClick = onSubmitClick,
                            text = "XÁC NHẬN",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Bạn có chắc chắn muốn xóa đề này",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = onDismissRequest,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "HỦY",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        CustomOutlinedButton(
                            onClick = onDeleteClick,
                            text = "XÓA",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OpenConfirmDialog(
    onDismissRequest: () -> Unit,
    onOpenClick: () -> Unit,
    @DrawableRes
    imageResourceId: Int,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(imageResourceId),
                    contentDescription = "fairy",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Bạn có chắc chắn muốn mở đề này",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(
                            onClick = onDismissRequest,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        ) {
                            Text(
                                text = "HỦY",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        CustomOutlinedButton(
                            onClick = onOpenClick,
                            text = "MỞ",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DisplayScorePreview() {
    QGenITheme(dynamicColor = false) {
        DisplayScore(
            message = "10/10",
            onNextButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteBoxLightPreview() {
    QGenITheme(dynamicColor = false) {
        DeleteBox(
            onDeleteClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_2), // Thay bằng hình ảnh của bạn
                contentDescription = "Sample Image",
            )
        }
    }
}

@Preview
@Composable
fun DeleteBoxDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {

    }
}

@Preview
@Composable
fun SubmitConfirmDialogLightPreview() {
    QGenITheme(dynamicColor = false) {
        SubmitConfirm(
            {},
            {},
            imageResourceId = R.drawable.reading_submit_confirm
        )
    }
}

@Preview
@Composable
fun SubmitConfirmDialogDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        SubmitConfirm(
            {},
            {},
            imageResourceId = R.drawable.reading_open_delete_confirm
        )
    }
}

@Preview
@Composable
fun OpenConfirmDialogLightPreview() {
    QGenITheme(dynamicColor = false) {
        OpenConfirmDialog(
            {},
            {},
            imageResourceId = R.drawable.reading_submit_confirm
        )
    }
}

@Preview
@Composable
fun OpenConfirmDialogDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        OpenConfirmDialog(
            {},
            {},
            imageResourceId = R.drawable.reading_open_delete_confirm
        )
    }
}

@Preview
@Composable
fun DeleteConfirmDialogLightPreview() {
    QGenITheme(dynamicColor = false) {
        DeleteConfirmDialog(
            {},
            {},
            imageResourceId = R.drawable.listening_submit_confirm
        )
    }
}

@Preview
@Composable
fun DeleteConfirmDialogDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        DeleteConfirmDialog(
            {},
            {},
            imageResourceId = R.drawable.listening_open_delete_confirm
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PracticeItemLightPreview() {
    QGenITheme(dynamicColor = false) {
        PracticeItemCard(
            ListeningPracticeItem(
                id = ObjectId(),
                title = "Capybara",
                creationDate = Date(),
                isNew = true,
                questionList = listOf()
            ),
            newIconResId = R.drawable.resource_new,
            onDeleteClick = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PracticeItemDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        PracticeItemCard(
            ListeningPracticeItem(
                id = ObjectId(),
                title = "Capybara",
                creationDate = Date(),
                isNew = true,
                questionList = listOf()
            ),
            newIconResId = R.drawable.resource_new,
            onDeleteClick = {}
        )
    }
}

