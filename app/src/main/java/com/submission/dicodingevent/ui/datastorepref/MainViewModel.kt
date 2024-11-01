package com.submission.dicodingevent.ui.datastorepref

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel (private val pref:Settingpref): ViewModel(){
    fun getThemeSet(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }
    fun saveThemeSet(isDrkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSett(isDrkModeActive)
        }
    }
}