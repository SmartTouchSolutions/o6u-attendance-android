package com.sts.o6uAttendance.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseObject<T>(
    @SerializedName("response")
    @Expose
    var responseMessage: T?
)