package com.example.guesswho.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.guesswho.R
import com.example.guesswho.viewmodel.MainViewModel
import androidx.compose.material3.MaterialTheme
private object MainPalette {
    val Purple80  = Color(0xFFD0BCFF)
    val Purple40  = Color(0xFF6650a4)
    val PurpleGrey80 = Color(0xFFCCC2DC)
    val Pink80    = Color(0xFFEFB8C8)

    val Black     = Color(0xFF000000)
    val Grey10    = Color(0xFF1C1B1F)
}

private val PurpleBlackColorScheme = darkColorScheme(
    primary         = MainPalette.Purple80,
    secondary       = MainPalette.PurpleGrey80,
    tertiary        = MainPalette.Pink80,
    background      = MainPalette.Black,
    surface         = MainPalette.Grey10,
    onPrimary       = MainPalette.Black,
    onBackground    = MainPalette.Purple80,
    onSurface       = MainPalette.Purple80
)

@Composable
fun MainScreen(
    onNavigateCreate: () -> Unit,
    onNavigateJoin: () -> Unit,
    vm: MainViewModel = hiltViewModel()
) {
    val state = vm.uiState.collectAsState().value

    /* wrap content in our custom theme */
    MaterialTheme(colorScheme = PurpleBlackColorScheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(MainPalette.Black, MainPalette.Grey10)
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            } else {
                Button(
                    onClick = onNavigateCreate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = stringResource(R.string.create_room))
                }

                OutlinedButton(
                    onClick = onNavigateJoin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(R.string.join_room))
                }
            }

            state.error?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style  = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Preview(name = "PurpleBlack Main", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewMainScreen() {
    MaterialTheme(colorScheme = PurpleBlackColorScheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Black, Color(0xFF1C1B1F))))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Guess Who",
                style = MaterialTheme.typography.headlineLarge,
                color = MainPalette.Purple80
            )
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainPalette.Purple80,
                    contentColor = Color.Black
                )
            ) {
                Text("Create room")
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Join with code", color = MainPalette.Purple80)
            }
        }
    }
}