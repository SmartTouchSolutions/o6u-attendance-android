package com.sts.o6uAttendance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @SerializedName("id") var id: Int,
    @SerializedName("username") var username: String,
    @SerializedName("email") var email: String,
    @SerializedName("type") var type: String,
    @SerializedName("Authorization") var token: String
)