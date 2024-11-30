package com.example.qgeni.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.qgeni.ui.screens.components.NextButton
import com.example.qgeni.ui.theme.QGenITheme

@Composable
fun WelcomeScreen(
    onNextButtonClick: () -> Unit,
    viewModel: WelcomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    if (uiState.firstInit) {
        viewModel.getPort(context)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        PageChanger(
            time = 4000L,
            modifier = Modifier.weight(1f)
        )
        Row {
            Spacer(modifier = Modifier.weight(2f))
            NextButton(
                onPrimary = true,
                onClick = {
                    viewModel.showDialog()
                }
            )
            Spacer(modifier = Modifier.weight(0.25f))
        }
        Spacer(modifier = Modifier.height(56.dp))
    }

    if (uiState.showDialog) {
        PortDialog(
            portDB = uiState.dbPort,
            portImage = uiState.genPort,
            onPortDBChange = { viewModel.updateDBPort(it) },
            onPortImageChange = { viewModel.updateGenPort(it) },
            onNextButtonClick = {
                viewModel.writePort(context = context)
                onNextButtonClick()
            }
        )
    }
}



@Preview(showSystemUi = true)
@Composable
fun WelcomeLightPreview() {
    QGenITheme(dynamicColor = false) {
        WelcomeScreen({})
    }
}

@Preview(showSystemUi = true)
@Composable
fun WelcomeDarkPreview() {
    QGenITheme(dynamicColor = false, darkTheme = true) {
        WelcomeScreen({})
    }
}

