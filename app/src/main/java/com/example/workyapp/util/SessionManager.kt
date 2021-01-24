package com.example.workyapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.workyapp.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager(context: Context) {

    companion object {
        const val USER_TOKEN = "user_token"
    }

    var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor?.putString(USER_TOKEN, token)
        editor?.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}