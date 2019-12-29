package com.sts.o6uAttendance.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AppDatabase
import com.sts.o6uAttendance.data.model.ResponseObject
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.data.model.SubjectDto
import com.sts.o6uAttendance.data.network.ApiResponse
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.ui.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    val apiService: ApiService = App.api!!,
    val appDatabase: AppDatabase = AppDatabase.getInstance()
) : ViewModel() {

    private val TAG: String = HomeViewModel::class.java.simpleName
    val homeData: MutableLiveData<SubjectDto?> by lazy { MutableLiveData<SubjectDto?>() }
    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val showToast: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }

    init {
        getSubjects()
    }

    fun getSubjects() {
        apiService.getSubjects(Authorization = appDatabase.userDao().load().token).enqueue(object
            : Callback<ApiResponse<ResponseObject<SubjectDto>>?> {
            override fun onFailure(
                call: Call<ApiResponse<ResponseObject<SubjectDto>>?>?,
                t: Throwable?
            ) {
                error.postValue("error happened")
            }

            override fun onResponse(
                call: Call<ApiResponse<ResponseObject<SubjectDto>>?>?,
                response: Response<ApiResponse<ResponseObject<SubjectDto>>?>?
            ) {
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
                        var subjects = response.body()!!.body!!.responseMessage!!.subjects

                            homeData.postValue(response.body()!!.body!!.responseMessage!!)
                            showToast.postValue("data received (Toast shows one times)")
                        if (subjects.size > 0) {
                            saveInDB(subjects)
                        }
                    } else {
                        error.postValue(response.errorBody()!!.string())
                    }
                }
            }

        })

    }

    private fun saveInDB(results: MutableList<Subject>) {
        for (i in results) {
            Log.d(TAG, "${i.subject} inserted to databade")
            appDatabase.subjectDao().save(i)
        }

    }
}