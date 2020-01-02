package com.sts.o6uAttendance.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.multidex.MultiDex
import com.sts.o6uAttendance.ui.util.SavePrefs
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.data.model.User
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.ui.splash.SplashActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class App : Application() {


    companion object {
        var api: ApiService? = null
        var instance: App by Delegates.notNull()
        fun logout(activity: Activity, progressBar_View: View) {
            progressBar_View.visibility = View.VISIBLE
            AccountData().clearUserData(instance)
            SavePrefs<User>(instance, User::class.java).clear()
            val intent = Intent(activity, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            activity.finish()
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()


        val client = httpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ApiService::class.java)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}