package com.sts.o6uAttendance.ui.util


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson

class SavePrefs<T>(activity: Context, private val cls: Class<*>) {
    private val userPrefsFileName: String = cls.name
    private val data = "DATA"
    private val prefs: SharedPreferences

    init {
        prefs = activity.getSharedPreferences(userPrefsFileName,
                MODE_PRIVATE)
    }

    fun save(obj: T) {
        val editor = prefs.edit()
        val data = Gson().toJson(obj)

        editor.putString(this.data, data)
        editor.apply()
    }

    fun load(): T {
        val data = prefs.getString(data, "")
        return Gson().fromJson(data, cls) as T
    }

    fun clear() {
        prefs.edit().clear().apply()
    }


}