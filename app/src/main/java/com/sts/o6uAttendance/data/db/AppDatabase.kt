package com.sts.o6uAttendance.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sts.o6uAttendance.core.App
import com.sts.o6uAttendance.data.model.Subject
import com.sts.o6uAttendance.data.model.User

@Database(entities = [User::class,Subject::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun subjectDao(): SubjectDao

    companion object {
        private const val DB_NAME = "Attendance.db"
        const val VERSION = 1
        private val instance: AppDatabase  by lazy { create(App.instance) }

        @Synchronized
        internal fun getInstance(): AppDatabase {
            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}