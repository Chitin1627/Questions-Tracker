package com.example.questionstracker.data

import com.example.questionstracker.database.QuestionsSolved


data class QuestionTrackerUiState(
    val date: String = "",
    val showDatePicker: Boolean = false,
    val noOfQuestions: Int = 0,
    val isInsertingData: Boolean = false,
    val questionsSolved: QuestionsSolved = QuestionsSolved()
)
