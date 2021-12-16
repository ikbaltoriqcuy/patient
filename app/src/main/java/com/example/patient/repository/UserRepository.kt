package com.example.patient.repository

import com.example.patient.data.User
import com.example.patient.data.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface InterUser {
    fun get(): User
    fun insert(data: User): Job
    fun delete()
}

class UserRepository(val user: UserDAO) : InterUser {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    override fun get(): User = user.get()

    override fun insert(data: User): Job = scope.launch(coroutineContext) {
        user.insert(data)
    }

    override fun delete() = user.delete()

}