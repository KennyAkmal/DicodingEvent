package com.submission.dicodingevent.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.submission.dicodingevent.data.db.db
import com.submission.dicodingevent.data.db.faventity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class vmdetail(application: Application) : AndroidViewModel(application) {
    private val dao = db.getDatabase(application).dao()

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun checkIfFavorite(eventId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteExists = dao.getFav(eventId) != null
            withContext(Dispatchers.Main) {
                _isFavorite.value = favoriteExists
            }
        }
    }

    fun toggleFavorite(event: faventity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_isFavorite.value == true) {
                dao.removeFav(event)
                _isFavorite.postValue(false)
            } else {
                dao.addFav(event)
                _isFavorite.postValue(true)
            }
        }
    }
}
