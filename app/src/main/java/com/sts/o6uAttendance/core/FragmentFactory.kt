package com.sts.o6uAttendance.core

import androidx.fragment.app.FragmentManager
import com.sts.o6uAttendance.ui.home.HomeFragment

object FragmentFactory{

    fun getHomeFragment(supportFragmentManager: FragmentManager): HomeFragment {
        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.FRAGMENT_NAME)
        if (fragment == null) {
            fragment = HomeFragment()
        }
        return fragment as HomeFragment
    }

}