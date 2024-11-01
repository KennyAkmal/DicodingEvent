package com.submission.dicodingevent.data.db;

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [faventity::class], version = 1, exportSchema = false)
abstract class db : RoomDatabase() {
    abstract fun dao(): dao
    companion object {
        @Volatile
        private var INSTANCE: db? = null

        fun getDatabase(context: Context): db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    db::class.java,
                    "favorite_event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
