package com.example.patient.repository

import com.example.patient.api.RetrofitCore
import com.example.patient.data.ApiResponse
import com.example.patient.data.PatientItem
import com.example.patient.data.Register
import org.json.JSONObject
import retrofit2.Response

class ApiCall {

    private val retrofitService1 by lazy { RetrofitCore.retrofitService(1) }
    private val retrofitService2 by lazy { RetrofitCore.retrofitService(2) }

    suspend fun register(username: String, password: String) : Response<ApiResponse<Register>> {
        return retrofitService1.register(
            username,
            password
        )
    }

    suspend fun login(username: String, password: String) : Response<JSONObject> {
        return retrofitService1.login(
            username,
            password
        )
    }

    suspend fun getAllPatients() : Response<List<PatientItem>> {
        return retrofitService2.getAllPatients()
    }
    suspend fun getPatient(id: String) : Response<PatientItem> {
        return retrofitService2.getPatient(id)
    }

    suspend fun addPatient(patientItem: PatientItem) : Response<JSONObject> {
        return retrofitService2.addPatient(
            patientItem.name,
            patientItem.avatar,
            patientItem.contact,
            patientItem.next_consultation,
            patientItem.address_2
        )
    }

    suspend fun deletePatient(
        id: String
    ) : Response<JSONObject> {
        return retrofitService2.deletePatient(id)
    }
}