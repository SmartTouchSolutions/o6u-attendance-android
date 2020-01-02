package com.sts.o6uAttendance.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sts.o6uAttendance.R
import com.sts.o6uAttendance.core.FragmentFactory
import com.sts.o6uAttendance.ui.home.subjects.SubjectsFragment

class MainActivity : AppCompatActivity() {
//    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment()

    }

    private fun showFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction
            .replace(
                R.id.container,
                FragmentFactory.getSubjectFragment(supportFragmentManager),
                SubjectsFragment.FRAGMENT_NAME
            )

        //        fragmentTransaction.addToBackStack(HomeFragment.FRAGMENT_NAME)
        fragmentTransaction.commit()
    }

}
