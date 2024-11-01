package com.submission.dicodingevent.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.submission.dicodingevent.data.db.db
import com.submission.dicodingevent.data.db.faventity

class Sharedvm(application: Application) : AndroidViewModel(application) {
    private val favoriteDao = db.getDatabase(application).dao()
    val favorites: LiveData<List<faventity>> = favoriteDao.getAllFav()
}
