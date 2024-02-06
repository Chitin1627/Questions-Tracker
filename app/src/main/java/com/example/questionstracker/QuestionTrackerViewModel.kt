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

    fun setNoOfQuestions(number: Int) {
        _uiState.update {currentState ->
            currentState.copy(
                noOfQuestions = number
            )
        }
    }

    fun getQuestionsSolved(date: String){
        var questionsSolved = dao.getQuestionsSolvedByDate(date)
        if(questionsSolved==null) {
            questionsSolved = QuestionsSolved(
                noOfQuestions = 0,
                date = date
            )
        }
        _uiState.update {currentState ->
            currentState.copy (
                questionsSolved = questionsSolved
            )
        }
    }

    fun getQuestionsSolvedLast30Days() {
        var num = dao.getQuestionsSolvedLast30Days()
        _uiState.update { currentState ->
            currentState.copy(
                noOfQuestionsLast30Days = num
            )
        }
    }

    fun setQuestionsSolved(date: String, noOfQuestions: Int) {
        val tempObj = QuestionsSolved(date = date, noOfQuestions = noOfQuestions)
        _uiState.update {currentState ->
            currentState.copy (
                questionsSolved = tempObj
            )
        }
    }

    fun setInsertingData(isInsertingData: Boolean) {
        _uiState.update { currentState ->
            currentState.copy (
                isInsertingData = isInsertingData
            )
        }
    }

    suspend fun upsertQuestionsSolved(data: QuestionsSolved) {
        dao.upsertQuestionsSolved(data)
    }
}