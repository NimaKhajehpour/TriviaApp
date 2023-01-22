package com.nima.triviaapp.screens

import android.text.Html
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nima.triviaapp.model.Questions
import com.nima.triviaapp.navigation.QuestionScreens
import com.nima.triviaapp.util.QuestionCategories
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriviaScreen(
    navController: NavController,
    viewModel: QuestionViewModel,
    amount: String?,
    category: String?,
    difficulty: String?,
    type: String?
){


    val questions = produceState<Questions?>(initialValue = null){
        value = viewModel.getQuestions(amount = amount!!,
            category = if (category!! == "null") "" else category,
            difficulty = if (difficulty!! == "null") "" else difficulty,
            type = if (type!! == "null") "" else type)
    }.value

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        shadowElevation = 1.dp
    ) {
        AnimatedVisibility(visible = questions == null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "Loading Your Questions",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = "Powered By: Open Trivia DB",
                        fontSize = 7.sp,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
        AnimatedVisibility(visible = questions?.response_code == 1) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "No Results Found, Sorry :(",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(text = "Powered By: Open Trivia DB",
                        fontSize = 7.sp,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .align(Alignment.CenterHorizontally)

                    )
                    Button(onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(
                                start = 16.dp, end = 16.dp,
                                top = 16.dp, bottom = 16.dp
                            )
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(text = "Go Back To Main Screen")
                    }
                }
            }
        }
        AnimatedVisibility(visible = questions?.response_code == 0) {
            val results = remember {
                mutableStateOf(questions?.results)
            }
            var questionIndex by remember {
                mutableStateOf(0)
            }
            var progress by remember {
                mutableStateOf(0)
            }
            var score by remember {
                mutableStateOf(0)
            }

            val questionDifficulty = Html.fromHtml(results.value!![questionIndex].difficulty).toString()

            val questionCategory = Html.fromHtml(results.value!![questionIndex].category).toString()

            val question = Html.fromHtml(results.value!![questionIndex].question).toString()

            val questionCorrectAnswer = Html.fromHtml(results.value!![questionIndex].correct_answer).toString()

            var questionChoices = remember(questionIndex) {
                if (results.value!![questionIndex].incorrect_answers.size > 1){
                    (results.value!![questionIndex].incorrect_answers +
                            listOf(results.value!![questionIndex].correct_answer)).shuffled(random = Random())
                }else{
                    if (results.value!![questionIndex].incorrect_answers[0] == "True".trim()){
                        results.value!![questionIndex].incorrect_answers +
                                listOf(results.value!![questionIndex].correct_answer)
                    }else{
                        listOf(results.value!![questionIndex].correct_answer) +
                            results.value!![questionIndex].incorrect_answers
                    }
                }

            }

            var choicesEnabled by remember {
                mutableStateOf(true)
            }

            var selectedChoiceIndex by remember {
                mutableStateOf(-1)
            }

            var correctChoice by remember {
                mutableStateOf(false)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$progress/$amount",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Text(text = "$score",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Card(
                    modifier = Modifier.padding(top = 16.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Text(text = question,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    top = 16.dp, start = 32.dp,
                                    end = 32.dp, bottom = 16.dp
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
                ) {
                    Badge(content = {
                            Text(
                                text = questionCategory,
                                fontSize = 9.sp,
                                modifier = Modifier.padding(5.dp)
                            ) },
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Badge(
                        content = {
                            Text(
                                text = questionDifficulty,
                                fontSize = 9.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.weight(1F))
                }

                if (!choicesEnabled) {
                    Card(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.LightGray)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            if (correctChoice){
                                Text(text = "Correct",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }else{
                                Text(text = "Incorrect",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Text(text = "Correct Answer is: $questionCorrectAnswer",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
                else {
                    Box(modifier = Modifier.size(0.dp))
                }

                questionChoices.forEachIndexed { index, it ->
                    ElevatedButton(onClick = {
                        correctChoice = Html.fromHtml(it).toString() == questionCorrectAnswer
                        selectedChoiceIndex = index
                        choicesEnabled = false
                    },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(10.dp),
                        enabled = choicesEnabled,
                        border = BorderStroke(1.dp, Color.LightGray),
                    ) {
                        Text(text = Html.fromHtml(it).toString())
                    }
                }
                Button(onClick = {
                    if (correctChoice){
                        score++
                    }
                     if (questionIndex != results.value!!.size -1){
                         choicesEnabled = true
                         selectedChoiceIndex = -1
                         correctChoice = false
                         progress++
                         questionIndex++
                     }else{
                         navController.navigate(
                             QuestionScreens.EndScreen.name+
                                     "/${amount}" +
                                     "/${when (category){
                                         "9" -> com.nima.triviaapp.util.QuestionCategories.NINE.category
                                         "10" -> com.nima.triviaapp.util.QuestionCategories.TEN.category
                                         "11" -> com.nima.triviaapp.util.QuestionCategories.ELEVEN.category
                                         "12" -> com.nima.triviaapp.util.QuestionCategories.TWELVE.category
                                         "13" -> com.nima.triviaapp.util.QuestionCategories.THIRTEEN.category
                                         "14" -> com.nima.triviaapp.util.QuestionCategories.FOURTEEN.category
                                         "15" -> com.nima.triviaapp.util.QuestionCategories.FIFTEEN.category
                                         "16" -> com.nima.triviaapp.util.QuestionCategories.SIXTEEN.category
                                         "17" -> com.nima.triviaapp.util.QuestionCategories.SEVENTEEN.category
                                         "18" -> com.nima.triviaapp.util.QuestionCategories.EIGHTEEN.category
                                         "19" -> com.nima.triviaapp.util.QuestionCategories.NINETEEN.category
                                         "20" -> com.nima.triviaapp.util.QuestionCategories.TWENTY.category
                                         "21" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_ONE.category
                                         "22" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_TWO.category
                                         "23" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_THREE.category
                                         "24" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_FOUR.category
                                         "25" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_FIVE.category
                                         "26" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_SIX.category
                                         "27" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_SEVEN.category
                                         "28" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_EIGHT.category
                                         "29" -> com.nima.triviaapp.util.QuestionCategories.TWENTY_NINE.category
                                         "30" -> com.nima.triviaapp.util.QuestionCategories.THIRTY.category
                                         "31" -> com.nima.triviaapp.util.QuestionCategories.THIRTY_ONE.category
                                         "32" -> com.nima.triviaapp.util.QuestionCategories.THIRTY_TWO.category
                                         else -> "Mixed"
                                     }
                                     }" +
                                     "/${
                                         if (difficulty != "null"){
                                             difficulty
                                         }else {
                                             "Mixed"
                                         }
                                     }" +
                                     "/${
                                         when(type){
                                             "boolean" -> "True - False"
                                             "multiple" -> "Multiple Choices"
                                             else -> "Mixed"
                                         }
                                     }" +
                                     "/${score}"
                         )
                     }
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !choicesEnabled,
                    border = BorderStroke(1.dp, Color.LightGray)


                ) {
                    if (questionIndex != results.value!!.size -1){
                        Text(text = "Next")
                    }else{
                        Text(text = "Finish")
                    }
                }
            }
        }
    }
}