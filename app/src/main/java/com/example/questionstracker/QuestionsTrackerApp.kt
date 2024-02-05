package com.example.questionstracker

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.questionstracker.ui.screens.DateChooseScreen
import com.example.questionstracker.ui.screens.InsertNoOfQuestions
import com.example.questionstracker.ui.screens.QuestionsSolvedCard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


enum class AppScreen(val title: String) {
    DateChoose(title = "date_choose"),
    ShowNoOfQuestions(title = "no_of_questions"),
    InsertNoOfQuestions(title = "insert_no_of_questions")
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun QuestionsTrackerApp(
    viewModel: QuestionTrackerViewModel,
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.DateChoose.name
    )

    NavHost(
        navController = navController,
        startDestination = AppScreen.DateChoose.name,
        modifier = Modifier
    ) {
        composable(route = AppScreen.DateChoose.name) {
            DateChooseScreen(
                date = uiState.date,
                showDatePicker = uiState.showDatePicker,
                onShowButtonClicked = {
                    viewModel.setDate(it)
                    viewModel.getQuestionsSolved(it)
                    navController.navigate(AppScreen.ShowNoOfQuestions.name)
                },
                onInsertButtonClicked = {
                    viewModel.setDate(it)
                    navController.navigate(AppScreen.InsertNoOfQuestions.name)
                },
                onDismiss = {viewModel.setShowDatePicker(it)}
            )
        }
        composable(route = AppScreen.ShowNoOfQuestions.name) {
            QuestionsSolvedCard(uiState.questionsSolved)
        }

        composable(route = AppScreen.InsertNoOfQuestions.name) {
            val coroutineScope = rememberCoroutineScope()
            InsertNoOfQuestions(
                date = uiState.date,
                onClick = {
                        viewModel.setNoOfQuestions(it)
                        viewModel.setQuestionsSolved(date = uiState.date, noOfQuestions = it)
                        viewModel.setInsertingData(true)
                }
            )
            if(uiState.isInsertingData) {
                coroutineScope.launch {
                    viewModel.upsertQuestionsSolved(uiState.questionsSolved)
                    viewModel.setInsertingData(false)
                    navController.navigateUp()
                }
            }
        }
    }

}