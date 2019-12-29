package com.sts.o6uAttendance.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDto(

    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("userDetails")
    @Expose
    var user: User
)

