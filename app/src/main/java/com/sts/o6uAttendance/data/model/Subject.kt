package com.sts.o6uAttendance.data.model

import com.google.gson.annotations.SerializedName


//@Entity(tableName = "Subject")
data class Subject(
    @SerializedName("created_at")
    var createdAt: String?, // 2019-12-17 22:00:00
//    @PrimaryKey
    @SerializedName("id")
    var id: Int?, // 2
    @SerializedName("lectures_count")
    var lecturesCount: Int?, // 0
    @SerializedName("subject_id")
    var subjectId: Int?, // 1
    @SerializedName("term")
    var term: String?, // 1-term 2020
    @SerializedName("updated_at")
    var updatedAt: String?, // 2019-12-16 22:00:00
    @SerializedName("users_id")
    var usersId: String?, // 3,2
    @SerializedName("subjects")
var subjectName: SubjectName?
)

data class SubjectName(
    @SerializedName("id")
    var id: Int?, // 2
    @SerializedName("name")
    var name: String? // english
)