package com.example.questionstracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Locale



@Composable
fun DatePickerButton(
    date: String,
    showDatePicker: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: (Boolean) -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Button(onClick = {onDismiss(true)}) {
            Text(text = "Choose the Date MF")
        }
    }

    if(showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = onDateSelected,
            onDismiss = {onDismiss(false)}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long) : Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.format(it)
        date
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(selectedDate)
                    onDismiss()
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
        ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDateButton() {
    //DatePickerButton()
}