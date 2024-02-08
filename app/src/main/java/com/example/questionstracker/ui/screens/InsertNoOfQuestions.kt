package com.example.questionstracker.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.questionstracker.R

@Composable
fun InsertNoOfQuestions(
    date: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var textState by remember { mutableStateOf("") }
    var isSubmitEnabled by remember { mutableStateOf(false) }

    isSubmitEnabled = textState!=""

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = "Date ($date)",
            onValueChange = {},
            readOnly = true,
            enabled = false,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = textState,
            label = { stringResource(R.string.no_of_questions) },
            onValueChange = {
                textState = it
            },
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                onClick(textState.toInt())
            },
            enabled = isSubmitEnabled
        )
        {
            Text(text = stringResource(R.string.done))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun previewScreen() {
    //insertNoOfQuestions(date = "22/02/2024")
}