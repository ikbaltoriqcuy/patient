package com.example.patient.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patient.base.BaseViewmodel
import com.example.patient.data.PatientItem
import com.example.patient.data.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewmodel : BaseViewmodel() {
    val responsePatients = MutableLiveData<List<PatientItem>>()
    val responsePatient = MutableLiveData<PatientItem>()
    val responseAddPatient = MutableLiveData<Boolean>()
    val responseDeletePatient = MutableLiveData<Boolean>()
    val responseFailed = MutableLiveData<String>()

    fun getAllPatient() {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.Main) {
                try {
                    val response = apiCall.getAllPatients()
                    if (response.isSuccessful) {
                        //response success 200
                        responsePatients.value = response.body()
                    } else {
                        //response success 400,500 etc
                        responseFailed.value = "failed"
                    }
                } catch (e:Exception) {
                    responseFailed.value = "failed"
                }
            }
        }
    }

    fun searchPatient(id: String) {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.Main) {
                try {
                    val response = apiCall.getPatient(id)
                    if (response.isSuccessful) {
                        //response success 200
                        responsePatient.value = response.body()
                    } else {
                        //response success 400,500 etc
                        responseFailed.value = "failed"
                    }
                } catch (e:Exception) {
                    responseFailed.value = "failed"
                }
            }
        }
    }

    fun deletePatient(id: String) {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.Main) {
                try {
                    val response = apiCall.deletePatient(id)
                    if (response.isSuccessful) {
                        //response success 200
                        responseDeletePatient.value = true
                    } else {
                        //response success 400,500 etc
                        responseDeletePatient.value = false
                        responseFailed.value = "failed"
                    }
                } catch (e:Exception) {
                    responseFailed.value = "failed"
                }
            }
        }
    }

    fun addPatient() {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.Main) {
                try {
                    val randomValue = (0..1000).random()
                    val response = apiCall.addPatient(
                        PatientItem(
                            "$randomValue",
                            "ikbal thor",
                            "Pauruan",
                            "Bandung",
                            "https://cdns.klimg.com/kapanlagi.com/s/twice/cover_twice.jpg",
                            "08346733434",
                            "2021-12-14T00:59:47.128Z",
                            "2021-12-14T00:59:47.128Z"
                        )
                    )
                    if (response.isSuccessful) {
                        //response success 200
                        responseAddPatient.value = true
                    } else {
                        //response success 400,500 etc
                        responseAddPatient.value = false
                        responseFailed.value = "failed"
                    }
                } catch (e: Exception) {
                    responseFailed.value = "failed"
                }
            }
        }
    }

    fun getUser(): User? {
        return userRepository.get()
    }
}