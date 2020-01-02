package com.sts.o6uAttendance.core

import androidx.fragment.app.FragmentManager
import com.sts.o6uAttendance.ui.home.lectures.LecturesFragment
import com.sts.o6uAttendance.ui.home.subjects.SubjectsFragment

object FragmentFactory{

    fun getSubjectFragment(supportFragmentManager: FragmentManager): SubjectsFragment {
        var fragment = supportFragmentManager.findFragmentByTag(SubjectsFragment.FRAGMENT_NAME)
        if (fragment == null) {
            fragment = SubjectsFragment()
        }
        return fragment as SubjectsFragment
    }

    fun getLectureFragment(supportFragmentManager: FragmentManager): LecturesFragment {
        var fragment = supportFragmentManager.findFragmentByTag(LecturesFragment.FRAGMENT_NAME)
        if (fragment == null) {
            fragment = LecturesFragment()
        }
        return fragment as LecturesFragment
    }
}