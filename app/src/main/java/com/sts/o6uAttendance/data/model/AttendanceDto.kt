package com.sts.o6uAttendance.data.model


import com.google.gson.annotations.SerializedName

data class AttendanceDto(
    @SerializedName("attendance")
    var attendance: List<Attendance?>?,
    @SerializedName("count_all_lectures_of_subject")
    var countAllLecturesOfSubject: Int?, // 10
    @SerializedName("studentsWithNoAttendance")
    var studentsWithNoAttendance: List<Student?>?
)