package com.example.questionstracker

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questionstracker.data.QuestionTrackerUiState
import com.example.questionstracker.database.QuestionsSolved
import com.example.questionstracker.database.QuestionsSolvedDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

class QuestionTrackerViewModel(
    private val dao: QuestionsSolvedDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionTrackerUiState())
    val uiState: StateFlow<QuestionTrackerUiState> = _uiState.asStateFlow()

    fun setDate(date: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = date
            )
        }
    }

    fun setShowDatePicker(isVisible: Boolean) {
        _uiState.update {currentState ->
            currentState.copy(
                showDatePicker = isVisible
            )
        }
    }

    fun setNoOfQuestions(number: Int) {
        _uiState.update {currentState ->
            currentState.copy(
                noOfQuestions = number
            )
        }
    }

    fun getQuestionsSolved(date: String){
        var questionsSolved = dao.getQuestionsSolvedByDate(date)
        if(questionsSolved==null) {
            questionsSolved = QuestionsSolved(date = date)
        }
        _uiState.update {currentState ->
            currentState.copy (
                questionsSolved = questionsSolved
            )
        }
    }


    fun getTotalQuestions() {
        val totalLeetcodeQuestions = dao.getLeetcodeTotalQuestions()
        val totalCodeforcesQuestions = dao.getCodeforcesTotalQuestions()
        val totalCodechefQuestions = dao.getCodechefTotalQuestions()
        val totalQuestionsMap: Map<String, Int> =  mapOf(
            Pair("Leetcode", totalLeetcodeQuestions),
            Pair("Codeforces", totalCodeforcesQuestions),
            Pair("CodeChef", totalCodechefQuestions)
        )
        _uiState.update { currentState ->
            currentState.copy(
                totalQuestionsMap = totalQuestionsMap
            )
        }
    }
    fun setQuestionsSolved(
        date: String,
        noOfLeetcode: Int,
        noOfCodeforces: Int,
        noOfCodechef: Int
    ) {
        val tempObj = QuestionsSolved(
            date = date,
            noOfLeetcode = noOfLeetcode,
            noOfCodechef = noOfCodechef,
            noOfCodeforces = noOfCodeforces
        )
        _uiState.update {currentState ->
            currentState.copy (
                questionsSolved = tempObj
            )
        }
    }

    fun setInsertingData(isInsertingData: Boolean) {
        _uiState.update { currentState ->
            currentState.copy (
                isInsertingData = isInsertingData
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchStats() {
        val noOfQuestionsLast30Days = dao.getQuestionsSolvedLast30Days()
        val totalActiveDays = dao.getTotalActiveDays()
        val highestStreak = dao.getHighestStreak().max()
        val data = dao.retrieveAllData()
        val dates = data.map {it.date}
        val streak = getCurrentStreak(dates)

        _uiState.update { currentState ->
            currentState.copy (
                noOfQuestionsLast30Days = noOfQuestionsLast30Days,
                totalActiveDays = totalActiveDays,
                highestStreak = highestStreak,
                streak = streak
            )
        }
    }


    suspend fun upsertQuestionsSolved(data: QuestionsSolved) {
        dao.upsertQuestionsSolved(data)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentStreak(dates: List<String>) : Int {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val dateObjects = dates.map {formatter.parse(it) }
        var currentStreak = 1
        for(i in 1 until dateObjects.size) {
            val previousDate = dateObjects[i]
            val currentDate = dateObjects[i-1]
            if (currentDate != null) {
                if(previousDate?.let { currentDate.isNextDay(it) } == true)  {
                    currentStreak++
                }
                else {
                    break
                }
            }
        }
        return currentStreak
    }

    private fun Date.isNextDay(otherDate: Date): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = otherDate
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val currDate = Date(this.time)
        val prevDate = Date(calendar.timeInMillis)
        Log.d("HELLO", currDate.toString())
        Log.d("HELLO2", prevDate.toString())
        return currDate==prevDate
    }
}

