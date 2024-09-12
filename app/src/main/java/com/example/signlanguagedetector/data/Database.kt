package com.example.signlanguagedetector.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.signlanguagedetector.data.dao.UserDao
import com.example.signlanguagedetector.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}