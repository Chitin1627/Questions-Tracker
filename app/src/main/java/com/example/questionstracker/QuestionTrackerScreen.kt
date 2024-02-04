package com.example.questionstracker

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questionstracker.database.QuestionsSolved
import com.example.questionstracker.ui.screens.DatePickerButton

@Composable
fun QuestionTrackerApp(
    viewModel: QuestionTrackerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    DatePickerButton(
        date = uiState.date,
        showDatePicker = uiState.showDatePicker,
        onDateSelected = {
            viewModel.setDate(it)
            val data = QuestionsSolved(date = "02/02/2024", noOfQuestions = 5)
            viewModel.upsertQuestionsSolved(data)
            Log.d("Hello", viewModel.getQuestionsSolved("02/02/2024")?.noOfQuestions.toString())
            },
        onDismiss = {viewModel.setShowDatePicker(it)}
    )
}