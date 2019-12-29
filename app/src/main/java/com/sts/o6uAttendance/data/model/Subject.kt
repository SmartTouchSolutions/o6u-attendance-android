package com.sts.o6uAttendance.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
data class Subject(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var subject: String
)