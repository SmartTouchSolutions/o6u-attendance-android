package com.sts.o6uAttendance.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sts.o6uAttendance.data.model.Subject

/**
 * Interface for database access for User related operations.
 */
@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(subject: Subject)

    @Query("SELECT * FROM Subject")
    fun load(): MutableList<Subject>

}
