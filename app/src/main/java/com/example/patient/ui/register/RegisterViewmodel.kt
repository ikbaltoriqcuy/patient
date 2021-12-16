package com.example.patient.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patient.base.BaseViewmodel
import com.example.patient.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewmodel : BaseViewmodel() {
    val responseRegister = MutableLiveData<Boolean>()
    val responseFailedRegister = MutableLiveData<String>()

    fun register(username: String, password: String, confirmPassword: String) {
        if (validation(username,password, confirmPassword)) {
            viewModelScope.launch(coroutineContext) {
                withContext(Dispatchers.Main) {
                    try {
                        val response = apiCall.register(username,password)
                        if (response.isSuccessful) {
                            //response success 200
                            responseRegister.value = true
                            val user = User(
                                response.body()?.data?.id ?: "",
                                response.body()?.data?.email ?: "",
                                ""
                            )
                            insertUser(user)
                        } else {
                            //response success 400,500 etcx
                            responseRegister.value = false
                            responseFailedRegister.value = "failed"
                        }
                    } catch (e: Exception) {
                        responseFailedRegister.value = "failed"
                    }
                }
            }
        } else {
            responseFailedRegister.value = "Password tidak sesuai dengan confirm atau password dan username kosong"
        }
    }

    fun validation(username: String, password: String, confirmPassword: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty() && password == confirmPassword
    }

    fun insertUser(user: User) {
        userRepository.insert(user)
    }
}