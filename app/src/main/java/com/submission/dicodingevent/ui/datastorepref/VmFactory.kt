package com.submission.dicodingevent.ui.datastorepref

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VmFactory(private val pref:Settingpref): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : "+ modelClass.name)
    }
}