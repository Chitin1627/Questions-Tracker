package com.example.questionstracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionstracker.data.QuestionTrackerUiState
import com.example.questionstracker.database.QuestionsSolved
import com.example.questionstracker.database.QuestionsSolvedDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionTrackerViewModel(
    private val dao: QuestionsSolvedDao
) : ViewModel() {
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
//    fun getQueryByDateOutput(output: QuestionsSolved) {
//        _uiState.update { currentState ->
//            currentState.copy(
//                questionsSolved = output
//            )
//        }
//    }

    fun getQuestionsSolved(date: String) : QuestionsSolved{
        return dao.getQuestionsSolvedByDate(date)
    }

    fun upsertQuestionsSolved(data: QuestionsSolved) {
        viewModelScope.launch {
            dao.upsertQuestionsSolved(data)
        }
    }
}