package com.sts.o6uAttendance.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.databinding.ActivityLoginBinding

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
        val errorObserver = Observer<String> { errorMessage ->
            // Update the UI, in this case, a TextView.
            Toast.makeText(this, "$errorMessage", Toast.LENGTH_LONG).show()
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.error.observe(this, errorObserver)
    }

}
