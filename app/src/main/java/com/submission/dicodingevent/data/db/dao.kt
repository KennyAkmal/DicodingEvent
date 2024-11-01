package com.submission.dicodingevent.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface dao {
    @Insert
    suspend fun addFav(event: faventity)

    @Delete
    suspend fun removeFav(event: faventity)

    @Query("SELECT * FROM favorite_events WHERE id = :id LIMIT 1")
    suspend fun getFav(id: Int): faventity?

    @Query("SELECT * FROM favorite_events")
    fun getAllFav(): LiveData<List<faventity>>
}
