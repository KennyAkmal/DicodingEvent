package com.submission.dicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.submission.dicodingevent.data.db.db
import com.submission.dicodingevent.data.db.faventity

class vmfav(application: Application) : AndroidViewModel(application) {
    private val favoriteDao = db.getDatabase(application).dao()
    val favorites: LiveData<List<faventity>> = favoriteDao.getAllFav()
}

