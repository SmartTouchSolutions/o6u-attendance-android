package com.sts.o6uAttendance.data.model


import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("username")
    var username: String?
)