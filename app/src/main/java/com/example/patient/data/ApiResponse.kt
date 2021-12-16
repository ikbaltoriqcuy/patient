package com.example.patient.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<T> (
    @SerializedName("success")  var success: String,
    @SerializedName("message")  var message: String,
    @SerializedName("data")  var data: T
)