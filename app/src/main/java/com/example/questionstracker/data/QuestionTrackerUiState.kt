package com.example.questionstracker.data

import com.example.questionstracker.database.QuestionsSolved
import kotlinx.coroutines.flow.emptyFlow
import java.util.concurrent.Flow

data class QuestionTrackerUiState(
    val date: String = "",
    val showDatePicker: Boolean = false,
    val questionsSolved: QuestionsSolved = QuestionsSolved()
)
