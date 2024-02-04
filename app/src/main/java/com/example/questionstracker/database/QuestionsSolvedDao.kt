package com.example.questionstracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface QuestionsSolvedDao {
    @Upsert
    suspend fun upsertQuestionsSolved(questionsSolved: QuestionsSolved)

    @Delete
    suspend fun deleteQuestionsSolved(questionsSolved: QuestionsSolved)

    @Query("SELECT * FROM QuestionsSolved WHERE date = :date")
    fun getQuestionsSolvedByDate(date: String): QuestionsSolved
}