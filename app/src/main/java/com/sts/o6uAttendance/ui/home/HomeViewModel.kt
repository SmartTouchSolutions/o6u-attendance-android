package com.sts.o6uAttendance.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.db.AccountData
import com.sts.o6uAttendance.data.model.Lecture
import com.sts.o6uAttendance.data.model.ResponseDto
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.data.model.User
import com.sts.o6uAttendance.data.network.ApiService
import com.sts.o6uAttendance.ui.home.lectures.LecturesAdapter
import com.sts.o6uAttendance.ui.home.subjects.SubjectAdapter
import com.sts.o6uAttendance.ui.util.SavePrefs
import com.sts.o6uAttendance.ui.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(
    private val apiService: ApiService = App.api!!

) : ViewModel()/*, CallBack<T> */{

//    private val TAG: String = HomeViewModel::class.java.simpleName

    var subjectsAdapter: SubjectAdapter = SubjectAdapter()
    var lecturesAdapter: LecturesAdapter = LecturesAdapter()

    val user = SavePrefs<User>(App.instance, User::class.java).load()
    private val _welcomeMessage: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val subjectsData: MutableLiveData<MutableList<Subject>?> by lazy { MutableLiveData<MutableList<Subject>?>() }
    val lecturesData: MutableLiveData<MutableList<Lecture>?> by lazy { MutableLiveData<MutableList<Lecture>?>() }

    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val subjectSelected: MutableLiveData<Subject> by lazy { MutableLiveData<Subject>() }

    val showToast: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }

    val welcomeMessage: LiveData<String> = _welcomeMessage

    init {
        welcomeMessage()
        getSubjects()
    }

    private fun welcomeMessage() {
        if (user.type == "doctor") {
            _welcomeMessage.postValue("Welcome Dr/ ${user.name}")

        } else {
            _welcomeMessage.postValue("Welcome Mr/ ${user.name}")
        }
    }


    private fun getSubjects() {

        apiService.getSubjects(Authorization = AccountData().getUserToken(App.instance))
            .enqueue(object
                : Callback<ResponseDto<MutableList<Subject>?>?> {
                override fun onFailure(
                    call: Call<ResponseDto<MutableList<Subject>?>?>?,
                    t: Throwable?
                ) {
                    error.postValue("error happened")
                }

                @SuppressLint("DefaultLocale")
                override fun onResponse(
                    call: Call<ResponseDto<MutableList<Subject>?>?>?,
                    response: Response<ResponseDto<MutableList<Subject>?>?>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                when {
                                    response.body()!!.success -> {
                                        val subjects = response.body()!!.data!!
                                        subjectsData.postValue(subjects)
//                                        showToast.postValue("data received (Toast shows one times)")
                                    }
                                    else -> {
                                        if (response.body()!!.error.toLowerCase().contains("token")) {
                                            error.postValue("token")
                                            AccountData().clearUserData(App.instance)
                                        }
                                        showToast.postValue(response.body()!!.error)
                                    }
                                }
                            }
                        } else {
                            showToast.postValue(response.errorBody()!!.string())
                        }
                    }
                }

            })

    }

    fun getLectures(): LiveData<MutableList<Lecture>?> {
        return lecturesData
    }
//
//    override fun notifier(obj: Subject) {
//        getLectures(obj)
//        subjectSelected.value = obj
//    }
//
//    override fun notifier(lecture: Lecture) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


}