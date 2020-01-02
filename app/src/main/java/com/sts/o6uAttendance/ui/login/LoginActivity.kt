package com.sts.o6uAttendance.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.databinding.ActivityLoginBinding
import com.sts.o6uAttendance.demo.activities.SubjectsDemoActivity

class LoginActivity : AppCompatActivity() {

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments

        binding.loginViewModel = viewModel


        // Create the observer which updates the UI.
        val userErrorObserver = Observer<String> { errorMessage ->
            // Update the UI, in this case, a TextView.
            binding.usernameTextLayout.error = errorMessage

        }
        val passwordErrorObserver = Observer<String> { errorMessage ->
            // Update the UI, in this case, a TextView.

            binding.passwordTextLayout.error = errorMessage
        }

        // Create the observer which updates the UI.
        val showToastObserver = Observer<String> { errorMessage ->
            // Update the UI, in this case, a TextView.
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

        // Create the observer which updates the UI.
        val homeDataObserver = Observer<String> {
            if (it == "Login") {
                val intent = Intent(this, SubjectsDemoActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }


        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.userError.observe(this, userErrorObserver)
        viewModel.passwordError.observe(this, passwordErrorObserver)
        viewModel.showToast.observe(this, showToastObserver)
        viewModel.homeData.observe(this, homeDataObserver)
    }

}
