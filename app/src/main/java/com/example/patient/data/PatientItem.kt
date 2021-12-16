package com.example.patient.data

import com.google.gson.annotations.SerializedName

data class PatientItem(
    val id: String,
    val name: String,
    @SerializedName("address")
    val address_1: String,
    @SerializedName("Address")
    val address_2: String,
    val avatar: String,
    val contact: String,
    val createdAt: String,
    val next_consultation: String
)