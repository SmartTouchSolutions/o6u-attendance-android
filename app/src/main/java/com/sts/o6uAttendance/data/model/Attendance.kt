package com.sts.o6uAttendance.data.model


import com.google.gson.annotations.SerializedName

data class Attendance(
    @SerializedName("count_all_lectures")
    var countAllLectures: Int?, // 7
    @SerializedName("student_attendance")
    var student: Student?,
    @SerializedName("student_id")
    var studentId: Int? // 7
)