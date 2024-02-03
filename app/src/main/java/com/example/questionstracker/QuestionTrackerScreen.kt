package com.example.questionstracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questionstracker.ui.screens.DatePickerButton

@Composable
fun QuestionTrackerApp(
    viewModel: QuestionTrackerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    DatePickerButton(
        date = uiState.date,
        showDatePicker = uiState.showDatePicker,
        onDateSelected = {viewModel.setDate(it)},
        onDismiss = {viewModel.setShowDatePicker(it)}
    )
}