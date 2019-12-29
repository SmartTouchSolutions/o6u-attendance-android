package com.sts.o6uAttendance.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubjectDto(

    @SerializedName("success")
    @Expose
    var success: Boolean,
    @PrimaryKey
    @SerializedName("message")
    @Expose
    var subjects: MutableList<Subject>
)

