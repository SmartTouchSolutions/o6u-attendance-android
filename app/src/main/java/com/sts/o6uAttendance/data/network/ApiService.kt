package com.sts.o6uAttendance.data.network


import com.sts.o6uAttendance.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    ///login
    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("username") email: String?,
        @Field("password") password: String?
    ): Call<ResponseDto<User>?>

    ///get Doctor Subjects
    @GET("get_doctor_subjects")
    fun getSubjects(@Header("Authorization") Authorization: String?)
            : Call<ResponseDto<MutableList<Subject>?>?>

    ///Create Lecture
    @POST("create_lecture")
    @FormUrlEncoded
    fun createLectures(
        @Header("Authorization") Authorization: String?,
        @Field("subject_user_id") id: String?
    ): Call<ResponseDto<String>?>

    ///get Lectures
    @POST("get_lecture")
    @FormUrlEncoded
    fun getLectures(
        @Header("Authorization") Authorization: String?,
        @Field("subject_user_id") id: String?
    ): Call<ResponseDto<MutableList<Lecture>?>?>

    ///create Attendance
    @POST("create_attendance")
    @FormUrlEncoded
    fun createAttendance(
        @Header("Authorization") Authorization: String?,
        @Field("subject_user_id") id: String?,
        @Field("student_code") student_code: String?,
        @Field("lecture_id") lecture_id: String?
    ): Call<ResponseDto<String?>?>

    ///get Attendance
    @POST("get_attendance")
    @FormUrlEncoded
    fun getAttendance(
        @Header("Authorization") Authorization: String?,
        @Field("subject_user_id") id: String?
    ): Call<ResponseDto<AttendanceDto?>?>

    ///logout
    @GET("logout")
    fun logout(@Header("Authorization") Authorization: String?)
            : Call<ResponseDto<String?>?>


}