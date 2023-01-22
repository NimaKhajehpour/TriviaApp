package com.nima.triviaapp.network

import com.nima.triviaapp.model.Questions
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface QuestionsApi {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: String,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithAmount(
        @Query("amount") amount: String,
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithCategory(
        @Query("amount") amount: String,
        @Query("category") category: String,
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithType(
        @Query("amount") amount: String,
        @Query("type") type: String
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithDifficulty(
        @Query("amount") amount: String,
        @Query("difficulty") difficulty: String,
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithoutCategory(
        @Query("amount") amount: String,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithoutDifficulty(
        @Query("amount") amount: String,
        @Query("category") category: String,
        @Query("type") type: String
    ): Questions

    @GET("api.php")
    suspend fun getQuestionsWithoutType(
        @Query("amount") amount: String,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String,
    ): Questions

}