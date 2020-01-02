package com.sts.o6uAttendance.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.data.model.ResponseDto
import com.sts.o6uAttendance.data.model.User
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.ui.util.SavePrefs
import com.sts.o6uAttendance.ui.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val apiService: ApiService = App.api!!
) : ViewModel() {
//    private val TAG: String = HomeViewModel::class.java.simpleName
    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    val homeData: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }
    val userError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val passwordError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val showToast: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }

    init {
      loading.postValue(false)
    }

    fun login() {
        loading.postValue(true)
        val username = username.value
        val password = password.value

        var valid = true

        if (username.isNullOrEmpty()) {
            userError.postValue("Username is required")
            valid = false
        }
        if (password.isNullOrEmpty()) {
            passwordError.postValue("Password is required")
            valid = false
        }
        if (!valid) {
            loading.postValue(false)
            return
        }
        apiService.login(username, password)
            .enqueue(object : Callback<ResponseDto<User>?> {
                override fun onFailure(
                    call: Call<ResponseDto<User>?>?,
                    t: Throwable?
                ) {
                    loading.postValue(false)
                    showToast.postValue("error happened :${t.toString()}")
                }

                override fun onResponse(
                    call: Call<ResponseDto<User>?>?,
                    response: Response<ResponseDto<User>?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                when {
                                    response.body()!!.success -> {
                                        AccountData().setUserToken(
                                            App.instance,
                                            response.body()!!.data!!.token,
                                            true
                                        )
                                        SavePrefs<User>(App.instance,User::class.java).save(response.body()!!.data!!)
                                        if (AccountData().isLogin(App.instance))
                                            homeData.postValue("Login")
                                    }
                                    else -> {
                                        loading.postValue(false)
                                        showToast.postValue(response.body()!!.error)
                                    }
                                }
                            }
                        } else {
                            loading.postValue(false)
                            showToast.postValue(response.errorBody()!!.string())
                        }
                    }
                }

            })

    }


}