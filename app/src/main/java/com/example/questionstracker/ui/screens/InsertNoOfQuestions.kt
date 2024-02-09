package com.example.questionstracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.questionstracker.R

@Composable
fun InsertNoOfQuestions(
    date: String,
    onClick: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var noOfLeetcode by remember { mutableStateOf("") }
    var noOfCodeforces by remember { mutableStateOf("") }
    var noOfCodechef by remember { mutableStateOf("") }
    var isSubmitEnabled by remember { mutableStateOf(false) }

    isSubmitEnabled = (noOfCodechef!="") && (noOfLeetcode!="") && (noOfCodechef!="")

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val newDate = "${date.subSequence(8, 10)}-${date.subSequence(5,7)}-${date.subSequence(0,4)}"
        OutlinedTextField(
            value = newDate,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = noOfLeetcode,
            label = { Text(text = stringResource(R.string.leetcode)) },
            onValueChange = {
                noOfLeetcode = it
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.leetcode_icon),
                    contentDescription = stringResource(id = R.string.no_of_leetcode_questions)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = noOfCodeforces,
            label = { Text(text = stringResource(R.string.codeforces)) },
            onValueChange = {
                noOfCodeforces = it
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.codeforces_icon),
                    contentDescription = stringResource(id = R.string.no_of_codeforces_questions)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = noOfCodechef,
            label = { Text(text = stringResource(R.string.codechef)) },
            onValueChange = {
                noOfCodechef = it
            },
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.codechef_icon),
                    contentDescription = stringResource(id = R.string.no_of_codechef_questions)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = {
                onClick(noOfLeetcode.toInt(), noOfCodeforces.toInt(), noOfCodechef.toInt())
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
fun PreviewScreen() {
    //insertNoOfQuestions(date = "22/02/2024")
}