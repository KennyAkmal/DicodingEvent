package com.submission.dicodingevent.data.db

class repofav(private val dao: dao) {
    suspend fun addFavorite(event: faventity) = dao.addFav(event)
    suspend fun removeFavorite(event: faventity) = dao.removeFav(event)
    suspend fun getFavorite(eventId: Int) = dao.getFav(eventId)
    suspend fun getAllFavorite() = dao.getAllFav()
}
