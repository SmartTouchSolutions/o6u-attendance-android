package com.sts.o6uAttendance.data.model


import com.google.gson.annotations.SerializedName

data class Lecture(
    @SerializedName("count_all_students")
    var countAllStudents: Int?, // 5
    @SerializedName("created_at")
    var createdAt: String?, // 2019-11-30 22:00:00
    @SerializedName("id")
    var id: Int? // 1
)