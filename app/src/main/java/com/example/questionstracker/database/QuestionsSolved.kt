package com.example.questionstracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionsSolved(
    val noOfQuestions: Int = 0,
    @PrimaryKey
    val date: String = "",
)
