package com.sts.o6uAttendance.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.databinding.ActivitySplashBinding
import com.sts.o6uAttendance.demo.activities.SubjectsDemoActivity
import com.sts.o6uAttendance.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val _splashDisplayLength: Int = 2000

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

//    val appDatabase: AppDatabase = AppDatabase.getDatabase(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments

        binding.viewModel= viewModel

        val homeData = Observer<Boolean> {
            Handler().postDelayed({
                // If there is already a token stored
                if (it) {
                    startActivity(Intent(this, SubjectsDemoActivity::class.java))
                    finish()
                } // If there is no token stored
                else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }, _splashDisplayLength.toLong())
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.homeData.observe(this, homeData)



    }
}
