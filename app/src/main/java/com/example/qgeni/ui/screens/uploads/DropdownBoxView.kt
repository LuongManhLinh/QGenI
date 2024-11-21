package com.example.qgeni.ui.screens.uploads

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qgeni.ui.theme.QGenITheme

/*
    DropdownBox để chọn model
 */

@Composable
fun SelectModelScreen(
    options: List<String>,
    selectedOption: String,
    onSelectedItemChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            colors = OutlinedTextFieldDefaults
                .colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiary
                ),
            readOnly = true,
            shape = RoundedCornerShape(size = 10.dp),
            label = { Text("Model đã chọn") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(start = 8.dp, end = 8.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                           },
                    onClick = {
                        onSelectedItemChange(option)
                        //selectedOption = option
                        expanded = !expanded
                    },
                    modifier = modifier
                        .background(Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp),
                        )

                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showSystemUi = true,)
@Composable
fun DropdownMenuPreview() {
    QGenITheme(dynamicColor = false) {
        val options = listOf("Model A", "Model B", "Model C")
        var selectedOption by remember { mutableStateOf("Chọn model") }
        SelectModelScreen(
            options = options,
            selectedOption = selectedOption,
            onSelectedItemChange = { option ->
                selectedOption = option
            }
        )
    }
}