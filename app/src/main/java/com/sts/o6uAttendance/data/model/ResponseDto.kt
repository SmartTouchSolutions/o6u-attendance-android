package com.sts.o6uAttendance.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseDto<T>(
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("error")
    @Expose
    var error: String,
    @SerializedName("data")
    @Expose
    var data: T?
)