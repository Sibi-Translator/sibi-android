package com.example.signlanguagedetector

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.signlanguagedetector.data.AppDatabase
import com.example.signlanguagedetector.data.model.User

object Global {
    var db : AppDatabase? = null
    var user : User? = null
    var sharedPreferences: SharedPreferences? = null

    suspend fun init(context: Context, activity: Activity) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database"
        ).build()
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        if(sharedPreferences != null) {
            val email = sharedPreferences!!.getString("user", "")
            user = db!!.userDao().getUserLogin(email ?: "")
        }
    }
}