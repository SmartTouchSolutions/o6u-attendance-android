package com.sts.o6uAttendance.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.sts.o6uAttendance.R
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar by lazy { toolbar_main_activity }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        showHomeFragment()
    }

//    private fun showHomeFragment() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.container,
//                FragmentFactory.getHomeFragment(supportFragmentManager),
//                HomeFragment.FRAGMENT_NAME
//            )
//        fragmentTransaction.addToBackStack(HomeFragment.FRAGMENT_NAME)
//        fragmentTransaction.commit()
//    }
}
