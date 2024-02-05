package com.example.questionstracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.questionstracker.database.QuestionsSolved

@Composable
fun QuestionsSolvedCard(
    questionObj: QuestionsSolved,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(72.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = questionObj.noOfQuestions.toString(),
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = questionObj.date,
                fontSize = 32.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign =  TextAlign.Center,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun CardPreview() {
    var tempObj = QuestionsSolved(date = "02/02/2024", noOfQuestions = 7)
    QuestionsSolvedCard(questionObj = tempObj)
}