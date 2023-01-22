package com.nima.triviaapp.di

import com.nima.triviaapp.network.QuestionsApi
import com.nima.triviaapp.repository.QuestionRepository
import com.nima.triviaapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuestionModule {

    @Provides
    @Singleton
    fun provideQuestionApi(): QuestionsApi =
        Retrofit.Builder().baseUrl(Constants.BaseUrl).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(QuestionsApi::class.java)

    @Provides
    @Singleton
    fun provideQuestionRepository(api: QuestionsApi) = QuestionRepository(api)
}