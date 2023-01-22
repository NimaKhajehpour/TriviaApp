package com.nima.triviaapp.repository

import android.util.Log
import com.nima.triviaapp.network.QuestionsApi
import com.nima.triviaapp.model.Questions
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionsApi) {
    private var data: Questions? = null

    suspend fun getQuestions(amount : String, category: String,
                             difficulty: String, type: String): Questions?{
        try {
            if (category.isEmpty() && difficulty.isEmpty() && type.isEmpty()){
                data = api.getQuestionsWithAmount(amount)
            }
            else if (category.isEmpty() && difficulty.isEmpty()){
                data = api.getQuestionsWithType(amount, type)
            }
            else if (category.isEmpty() && type.isEmpty()){
                data = api.getQuestionsWithDifficulty(amount, difficulty)
            }
            else if (type.isEmpty() && difficulty.isEmpty()){
                data = api.getQuestionsWithCategory(amount, category)
            }
            else if (category.isEmpty()){
                data = api.getQuestionsWithoutCategory(amount, difficulty, type)
            }
            else if (difficulty.isEmpty()){
                data = api.getQuestionsWithoutDifficulty(amount, category, type)
            }
            else if (type.isEmpty()){
                data = api.getQuestionsWithoutType(amount, category, difficulty)
            }
            else{
                data = api.getQuestions(amount, category, difficulty, type)
            }

        }catch (e: Exception){
            Log.d("Repository", "getQuestions: ${e}")
        }
        return data
    }
}