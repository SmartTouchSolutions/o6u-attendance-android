package com.sts.o6uAttendance.ui.splash

import androidx.lifecycle.ViewModel
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.ui.util.SingleLiveEvent

class SplashViewModel : ViewModel() {

//    private val TAG: String = HomeViewModel::class.java.simpleName
    val homeData: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }
//    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
//    val showToast: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }


    init {
        checkUserData()
    }

    private fun checkUserData() {
        homeData.postValue(AccountData().isLogin(App.instance))
//        if (App.instance.getSharedPreferences("data", Context.MODE_PRIVATE).getString(
//                "token",
//                "NO_TOKEN"
//            ) != "NO_TOKEN" ||
//            App.instance.getSharedPreferences("data", Context.MODE_PRIVATE).getString(
//                "user_type",
//                "NO_TYPE"
//            ) != "NO_TYPE"
//        ) {
//            homeData.postValue(true)
//        }
    }
}