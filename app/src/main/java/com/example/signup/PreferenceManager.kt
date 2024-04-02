package com.example.signup

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "UserPrefs"
    private const val KEY_USERNAME = "username"
    private const val KEY_PASSWORD = "password"
    private const val KEY_USERTYPE = "userType"

    fun saveUser(context: Context, username: String, password: String, userType: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.putString(KEY_USERTYPE, userType)
        editor.apply()
    }

    fun getUser(context: Context): User? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val username = prefs.getString(KEY_USERNAME, null)
        val password = prefs.getString(KEY_PASSWORD, null)
        val userType = prefs.getString(KEY_USERTYPE, null)

        return if (username != null && password != null && userType != null) {
            User(username, password, userType)
        } else {
            null
        }
    }

    fun updateUserInfo(context: Context, newUsername: String, newPassword: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(KEY_USERNAME, newUsername)
        editor.putString(KEY_PASSWORD, newPassword)
        editor.apply()
    }
}

