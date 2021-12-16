package com.example.patient.base

import androidx.lifecycle.ViewModel
import com.example.patient.repository.ApiCall
import com.example.patient.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewmodel : ViewModel() {
    private val coroutineJob = SupervisorJob()
    val coroutineContext = Dispatchers.IO + coroutineJob
    val apiCall = ApiCall()

    val userRepository = object: KoinComponent {
        val repository: UserRepository by inject()
    }.repository
}