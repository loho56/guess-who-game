package com.example.guesswho.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guesswho.domain.model.Player
import com.example.guesswho.domain.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateRoomUiState(
    val availableThemes: List<String> = listOf("Cartoon", "Animals", "Food"),
    val selectedTheme: String = "Cartoon",
    val dropDownExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val repo: GameRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRoomUiState())
    val uiState: StateFlow<CreateRoomUiState> = _uiState.asStateFlow()

    fun onDropDownToggle(expanded: Boolean) {
        _uiState.update { it.copy(dropDownExpanded = expanded) }
    }

    fun onThemeSelected(theme: String) {
        _uiState.update {
            it.copy(selectedTheme = theme, dropDownExpanded = false)
        }
    }

    fun createRoom(onSuccess: (roomCode: String) -> Unit) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        runCatching {
            val player = Player(auth.uid!!, "Player-${System.currentTimeMillis() % 1000}")
            repo.createGame(player, uiState.value.selectedTheme)
        }.onSuccess { roomCode ->
            onSuccess(roomCode)
        }.onFailure { ex ->
            _uiState.update { it.copy(isLoading = false, error = ex.message) }
        }
    }
}