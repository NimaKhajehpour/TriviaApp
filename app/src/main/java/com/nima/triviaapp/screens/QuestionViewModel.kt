package com.nima.triviaapp.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.triviaapp.model.Questions
import com.nima.triviaapp.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel (private val repository: QuestionRepository): ViewModel() {

    suspend fun getQuestions(amount : String, category: String,
                             difficulty: String, type: String): Questions?{
        return repository.getQuestions(amount, category, difficulty, type)
    }
}