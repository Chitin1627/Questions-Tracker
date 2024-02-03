package com.example.questionstracker

import androidx.lifecycle.ViewModel
import com.example.questionstracker.data.QuestionTrackerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuestionTrackerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionTrackerUiState())
    val uiState: StateFlow<QuestionTrackerUiState> = _uiState.asStateFlow()

    fun setDate(date: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setShowDatePicker(isVisible: Boolean) {
        _uiState.update {currentState ->
            currentState.copy(
                showDatePicker = isVisible
            )
        }
    }
}