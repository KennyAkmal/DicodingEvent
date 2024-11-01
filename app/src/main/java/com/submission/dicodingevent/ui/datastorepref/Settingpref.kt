package com.submission.dicodingevent.ui.datastorepref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
class Settingpref private constructor(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>){
    private val themeKey = booleanPreferencesKey("theme_setting")
    fun getThemeSetting():kotlinx.coroutines.flow.Flow<Boolean>{
        return dataStore.data.map { preferences -> preferences[themeKey]?:false
        }
    }

    suspend fun saveThemeSett(isDrkModeActive: Boolean){
        dataStore.edit { preferences -> preferences [themeKey] = isDrkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Settingpref? = null

        fun getInstance(dataStore: DataStore<androidx.datastore.preferences.core.Preferences>):Settingpref{
            return INSTANCE?: synchronized(this){
                val instance = Settingpref(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}