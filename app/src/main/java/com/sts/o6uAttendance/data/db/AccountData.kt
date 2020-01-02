package com.sts.o6uAttendance.data.db

import android.content.Context

class AccountData {

    fun setUserToken(activity: Context, UserToken: String, ActivityLogin: Boolean) {
        val sharedPref = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
        sharedPref.putString("UserToken", UserToken)
        sharedPref.putBoolean("ActivityLogin", ActivityLogin)
        sharedPref.apply()
        sharedPref.commit()
    }


    fun getUserToken(activity: Context): String {
        val sharedPref = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val restoredText = sharedPref.getString("UserToken", null)
        return restoredText ?: "No User Token"
    }

    fun isLogin(activity: Context): Boolean {
        val sharedPref = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("ActivityLogin", false)
    }

    fun clearUserData(activity: Context) {
        val sharedPref = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
        sharedPref.clear()
        sharedPref.apply()
    }

}