package com.example.patient.di

import com.example.patient.data.LocalDB
import com.example.patient.repository.UserRepository
import com.example.patient.ui.home.HomeViewmodel
import com.example.patient.ui.login.LoginViewmodel
import com.example.patient.ui.register.RegisterViewmodel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val injectionModule = module {
    single { LocalDB.getDatabase(androidApplication()) }
    single { get<LocalDB>().userDAO() }

    single<UserRepository>{UserRepository(get())}

    viewModel { LoginViewmodel() }
    viewModel { RegisterViewmodel() }
    viewModel { HomeViewmodel() }
}