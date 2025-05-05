package com.nima.triviaapp.di

import com.nima.triviaapp.network.QuestionsApi
import com.nima.triviaapp.repository.QuestionRepository
import com.nima.triviaapp.screens.QuestionViewModel
import com.nima.triviaapp.util.Constants
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        Retrofit.Builder().baseUrl(Constants.BaseUrl).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(QuestionsApi::class.java)
    }
    single {
        QuestionRepository(get())
    }

    viewModel {
        QuestionViewModel(get())
    }
}