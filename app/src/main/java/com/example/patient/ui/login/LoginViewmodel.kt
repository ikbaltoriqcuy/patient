package com.example.patient.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patient.base.BaseViewmodel
import com.example.patient.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewmodel : BaseViewmodel() {
    val responseLogin = MutableLiveData<Boolean>()
    val responseFailedLogin = MutableLiveData<String>()

    fun login(username: String, password: String) {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.Main) {
                try {
                    val response = apiCall.login(username, password)
                    if (response.isSuccessful) {
                        //response success 200
                        insertUser(
                            User(
                                "",
                                username,
                                password
                            )
                        )
                        responseLogin.value = true
                    } else {
                        //response success 400,500 etc
                        responseLogin.value = false
                        responseFailedLogin.value = "failed"
                    }
                } catch (e:Exception) {
                    responseFailedLogin.value = "failed"
                }
            }
        }
    }

    fun insertUser(user: User) {
        userRepository.insert(user)
    }
}