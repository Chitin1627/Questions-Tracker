package com.example.questionstracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface QuestionsSolvedDao {
    @Upsert
    suspend fun upsertQuestionsSolved(questionsSolved: QuestionsSolved)

    @Query("DELETE FROM QuestionsSolved")
    suspend fun deleteAllQuestionsSolved()

    @Query("SELECT * FROM QuestionsSolved WHERE date = :date")
    fun getQuestionsSolvedByDate(date: String): QuestionsSolved

    @Query("SELECT SUM(noOfLeetcode)+SUM(noOfCodeChef)+SUM(noOfCodeforces) FROM QuestionsSolved WHERE date > date('now','-30 days')")
    fun getQuestionsSolvedLast30Days(): Int
}