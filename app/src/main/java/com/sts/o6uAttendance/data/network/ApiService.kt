package com.sts.o6uAttendance.data.network


import com.sts.o6uAttendance.data.model.ResponseObject
import com.sts.o6uAttendance.data.model.SubjectDto
import com.sts.o6uAttendance.data.model.UserDto
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    ///login
    @POST("login")
    @FormUrlEncoded
    fun login(@Field("username") email: String?,
              @Field("password") password: String?)
            : Call<ApiResponse<ResponseObject<UserDto>>?>


    ///get Doctor Subjects
    @POST("get_doctor_subjects")
    @FormUrlEncoded
    fun getSubjects(@Header("Authorization") Authorization: String?)
            : Call<ApiResponse<ResponseObject<SubjectDto>>?>

}