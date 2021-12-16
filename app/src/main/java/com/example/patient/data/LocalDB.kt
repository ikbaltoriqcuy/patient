package com.example.patient.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class
    ], version = 2, exportSchema = false
)
abstract class LocalDB : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: LocalDB? = null

        fun getDatabase(
            context: Context
        ): LocalDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    "test.db"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

        fun clearAllTable() {
            INSTANCE?.clearAllTables()
        }

    }
}