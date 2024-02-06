package com.example.questionstracker.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Locale


//@Composable
//fun DateChooseScreen(
//    date: String,
//    showDatePicker: Boolean,
//    onShowButtonClicked: (String) -> Unit,
//    onInsertButtonClicked: (String) -> Unit,
//    onDismiss: (Boolean) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center)
//    {
//        DatePickerButton(
//            date = date,
//            showDatePicker = showDatePicker,
//            onDateSelected = onInsertButtonClicked,
//            onDismiss = onDismiss,
//            text = "Insert"
//        )
//
//        DatePickerButton(
//            date = date,
//            showDatePicker = showDatePicker,
//            onDateSelected = onShowButtonClicked,
//            onDismiss = onDismiss,
//            text = "Show"
//        )
//    }
//}

@Composable
fun DateChooseScreen(
    date: String,
    showDatePicker: Boolean,
    onShowButtonClicked: (String) -> Unit,
    onInsertButtonClicked: (String) -> Unit,
    onDismiss: (Boolean) -> Unit,
) {
    var selectedValue by rememberSaveable {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            selectedValue = 1
            onDismiss(true)
        }
        ) {
            Text(text = "Insert")
        }

        Button(onClick = {
            selectedValue = 2
            onDismiss(true)
        }
        ) {
            Text(text = "Show")
        }
    }

    if(showDatePicker) {
        if(selectedValue==1) {
            MyDatePickerDialog(
                onDateSelected = onInsertButtonClicked,
                onDismiss = {onDismiss(false)}
            )
        }
        else if(selectedValue==2) {
            MyDatePickerDialog(
                onDateSelected = onShowButtonClicked,
                onDismiss = {onDismiss(false)}
            )
        }

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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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