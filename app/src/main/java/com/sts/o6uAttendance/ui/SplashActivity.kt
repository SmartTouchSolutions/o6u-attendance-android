package com.sts.o6uAttendance.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val _splashDisplayLength: Int = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            // If there is already a token stored
            if (getSharedPreferences("data", Context.MODE_PRIVATE).getString("token", "NO_TOKEN") != "NO_TOKEN" ||
                getSharedPreferences("data", Context.MODE_PRIVATE).getString("user_type", "NO_TYPE") != "NO_TYPE") {
                startActivity(Intent(this , MainActivity::class.java))
                finish()
            } // If there is no token stored
            else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, _splashDisplayLength.toLong())
    }
}
