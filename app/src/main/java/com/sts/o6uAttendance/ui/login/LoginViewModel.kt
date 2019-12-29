package com.sts.o6uAttendance.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AppDatabase
import com.sts.o6uAttendance.data.model.ResponseObject
import com.sts.o6uAttendance.data.model.User
import com.sts.o6uAttendance.data.model.UserDto
import com.sts.o6uAttendance.data.network.ApiResponse
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.ui.home.HomeViewModel
import com.sts.o6uAttendance.ui.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    private val apiService: ApiService = App.api!!,
    private val appDatabase: AppDatabase = AppDatabase.getInstance()
) : ViewModel() {
    private val TAG: String = HomeViewModel::class.java.simpleName
    var username: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    val homeData: MutableLiveData<UserDto> by lazy { MutableLiveData<UserDto>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val showToast: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }

//    init {
//        login(username = username.toString(),password = password.toString())
//    }

    fun login() {
        apiService.login(username.value,password.value).enqueue(object : Callback<ApiResponse<ResponseObject<UserDto>>?>{
            override fun onFailure(call: Call<ApiResponse<ResponseObject<UserDto>>?>?, t: Throwable?) {
                error.postValue("error happened :${t.toString()}")
            }

            override fun onResponse(
                call: Call<ApiResponse<ResponseObject<UserDto>>?>?,
                response: Response<ApiResponse<ResponseObject<UserDto>>?>?) {
                Log.d(TAG, "onResponse() called with: call = [$call], response = [$response]")
                Log.d(
                    TAG,
                    "isSuccessful : " + response?.isSuccessful
                            + " message : " + response?.message()
                            + " code : " + response?.code()
                            + " raw : " + response?.raw()
                )

                if (response != null) {
                    if (response.isSuccessful) {
                        homeData.postValue(response.body()?.body?.responseMessage)
                        showToast.postValue("data received (Toast shows one times)")
                        if (response.body()!!.isSuccessful) {
                            saveInDB(response.body()!!.body?.responseMessage?.user!!)
                        }

                    } else {
                        error.postValue(response.errorBody()!!.string())
                    }
                }
            }

        })

    }

    private fun saveInDB(user: User) {
            appDatabase.userDao().save(user)

    }
}