package com.example.guesswho.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.guesswho.viewmodel.CreateRoomViewModel

/* ------------------------------------------------------------------
   Purple + Black palette (same as MainScreen)
------------------------------------------------------------------ */
private object Palette {
    val Purple80 = Color(0xFFD0BCFF)
    val Purple40 = Color(0xFF6650a4)
    val Black    = Color(0xFF000000)
    val Grey10   = Color(0xFF1C1B1F)
}

/* ------------------------------------------------------------------
   The screen
------------------------------------------------------------------ */
@Composable
fun CreateRoomScreen(
    onRoomCreated: (roomCode: String) -> Unit,   // caller navigates
    onBack: () -> Unit,                          // caller pops back-stack
    vm: CreateRoomViewModel = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary     = Palette.Purple80,
            background  = Palette.Black,
            surface     = Palette.Grey10,
            onPrimary   = Palette.Black,
            onBackground= Palette.Purple80,
            onSurface   = Palette.Purple80
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Palette.Black, Palette.Grey10)
                    )
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Create a room",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            // theme picker
            ExposedDropdownMenuBox(
                expanded = state.dropDownExpanded,
                onExpandedChange = vm::onDropDownToggle
            ) {
                OutlinedTextField(
                    value = state.selectedTheme,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Theme") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.dropDownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                )
                ExposedDropdownMenu(
                    expanded = state.dropDownExpanded,
                    onDismissRequest = { vm.onDropDownToggle(false) }
                ) {
                    state.availableThemes.forEach { theme ->
                        DropdownMenuItem(
                            text = { Text(theme) },
                            onClick = { vm.onThemeSelected(theme) }
                        )
                    }
                }
            }

            // buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Back")
                }

                Button(
                    onClick = { vm.createRoom(onRoomCreated) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Create")
                    }
                }
            }

            state.error?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/* ------------------------------------------------------------------
   Preview while designing
------------------------------------------------------------------ */
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewCreateRoom() {
    CreateRoomScreen(onRoomCreated = {}, onBack = {})
}