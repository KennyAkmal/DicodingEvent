package com.submission.dicodingevent.data.retroit

import com.submission.dicodingevent.data.respon.EventFinishedRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIservice {
    @GET("events")
    fun getEvnt(
        @Query("active") active: Int
    ): Call<EventFinishedRes>
}