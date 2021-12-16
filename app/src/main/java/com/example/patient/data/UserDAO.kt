package com.example.patient.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun get(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: User)

    @Query("DELETE FROM user")
    fun delete()
}