package com.nima.triviaapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.nima.triviaapp.R
import com.nima.triviaapp.navigation.QuestionScreens
import com.nima.triviaapp.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
){
    var amountValueState by rememberSaveable {
        mutableStateOf(1f)
    }

    var questionTypeState by rememberSaveable{
        mutableStateOf(0f)
    }

    var difficultyState by rememberSaveable{
        mutableStateOf(0f)
    }

    var categoryMenuExpandedState by remember {
        mutableStateOf(false)
    }

    var selectedCategory by rememberSaveable {
        mutableStateOf(0)
    }

    var categoryText by rememberSaveable {
        mutableStateOf(Constants.categoryList[0])
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(15.dp),
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Challenge Yourself",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(top = 32.dp),
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Powered By: Open Trivia DB",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
            Card(
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                shape =  RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Questions Amount: ${amountValueState.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(onClick = {
                                                     if (amountValueState > 1){
                                                         amountValueState--
                                                     }
                        },
                            modifier = Modifier
                                .padding(start = 32.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_outline_remove_24),
                                contentDescription = null,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1F))
                        IconButton(onClick = {
                                                     if (amountValueState < 50){
                                                         amountValueState++
                                                     }
                        },
                            modifier = Modifier
                                .padding(end = 32.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null)
                        }
                    }
                    Slider(
                        value = amountValueState,
                        onValueChange = {value ->
                            amountValueState = value },
                        valueRange = 1f..50f,
                        onValueChangeFinished = {amountValueState = amountValueState.toInt().toFloat()},
                        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 8.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                shape =  RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Select Question Type: ${
                        when(questionTypeState){
                            0f -> "Mixed"
                            1f -> "Multiple Choices"
                            2f-> "True / False"
                            else -> ""
                        }
                    }",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Slider(value = questionTypeState,
                        onValueChange = {
                            questionTypeState = it
                        },
                        valueRange = 0f..2f,
                        steps = 1,
                        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp)
                    )

                }
            }
            Card(
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                shape =  RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Select Your Difficulty: ${
                        when(difficultyState){
                            0f -> "Mixed"
                            1f -> "Easy"
                            2f -> "Medium"
                            3f -> "Hard"
                            else -> ""
                        }
                    }",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Slider(value = difficultyState,
                        onValueChange = {
                            difficultyState = it
                        },
                        valueRange = 0f..3f,
                        steps = 2,
                        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                shape =  RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, color = Color.LightGray)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Select A Category",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    ExposedDropdownMenuBox(expanded = categoryMenuExpandedState,
                        onExpandedChange = { categoryMenuExpandedState = !categoryMenuExpandedState },
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp)
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = categoryText,
                            textStyle = MaterialTheme.typography.bodySmall,
                            onValueChange = {},
                            label = { Text("Categories") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryMenuExpandedState) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(expanded = categoryMenuExpandedState,
                            onDismissRequest = { categoryMenuExpandedState = false }
                        ) {
                            Constants.categoryList.forEachIndexed{index, category ->
                                DropdownMenuItem(text = { Text(text = category) },
                                    onClick = {
                                        if (index > 0){
                                            selectedCategory = index + 8
                                        }
                                        else {selectedCategory = 0}
                                        categoryText = category
                                        categoryMenuExpandedState = false
                                    }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                            }
                        }
                    }
                }
            }
            Button(
                onClick =
                {
                    var categoryToReturn = ""
                    var difficultyToReturn = when(difficultyState){
                        1f -> "easy"
                        2f -> "medium"
                        3f -> "hard"
                        else -> ""
                    }
                    var typeToReturn = when(questionTypeState){
                        1f -> "multiple"
                        2f -> "boolean"
                        else -> ""
                    }
                    if (selectedCategory != 0)
                        categoryToReturn = selectedCategory.toString()
                    else
                        categoryToReturn = ""

                navController.navigate(QuestionScreens.TriviaScreen.name+
                        "/${amountValueState.toInt()}/${categoryToReturn}/${difficultyToReturn}/${typeToReturn}")
                },
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp, bottom = 32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(text = "Start Trivia")
            }
        }
    }
}