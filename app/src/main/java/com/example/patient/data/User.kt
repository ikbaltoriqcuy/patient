package com.example.patient.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User (
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "username")
    var username: String = "",
    @ColumnInfo(name = "password")
    var password: String = "",
)