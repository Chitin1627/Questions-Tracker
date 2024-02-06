package com.example.questionstracker

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import kotlinx.coroutines.launch


enum class AppScreen(val title: String) {
    DateChoose(title = "Question Tracker"),
    ShowNoOfQuestions(title = "No of Questions"),
    InsertNoOfQuestions(title = "Insert No of Questions")
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun QuestionsTrackerApp(
    viewModel: QuestionTrackerViewModel,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.DateChoose.name
    )
    viewModel.getQuestionsSolvedLast30Days()
    Scaffold(
        topBar = {
            QuestionsTrackerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    ) {innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = AppScreen.DateChoose.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.DateChoose.name) {
                DateChooseScreen(
                    date = uiState.date,
                    showDatePicker = uiState.showDatePicker,
                    noOfQuestionsLast30Days = uiState.noOfQuestionsLast30Days,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsTrackerAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
        )
}