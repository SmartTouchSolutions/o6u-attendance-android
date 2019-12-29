package com.sts.o6uAttendance.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sts.o6uAttendance.data.model.User

/**
 * Interface for database access for User related operations.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user")
    fun load(): User

}
