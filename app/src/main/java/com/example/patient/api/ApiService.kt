package com.example.patient.api

import com.example.patient.data.ApiResponse
import com.example.patient.data.PatientItem
import com.example.patient.data.Register
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") Password: String
    ): Response<ApiResponse<Register>>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<JSONObject>

    @GET("patients")
    suspend fun getAllPatients(): Response<List<PatientItem>>

    @GET("patients/{id}")
    suspend fun getPatient(@Path("id") id:String): Response<PatientItem>

    @FormUrlEncoded
    @POST("patients")
    suspend fun addPatient(
        @Field("name") name: String,
        @Field("avatar") avatar: String,
        @Field("contact") contact: String,
        @Field("next_consultation") next_consultation: String,
        @Field("address") address: String,
    ): Response<JSONObject>

    @DELETE("patients/{id}")
    suspend fun deletePatient(@Path("id") id:String): Response<JSONObject>
}