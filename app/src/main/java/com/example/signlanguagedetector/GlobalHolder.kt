package com.example.signlanguagedetector

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.room.Room
import com.example.signlanguagedetector.data.AppDatabase
import com.example.signlanguagedetector.data.model.User
import com.example.signlanguagedetector.ui.screen.Intro
import com.example.signlanguagedetector.ui.screen.Login

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
            val email = sharedPreferences!!.getString("email", "")
            user = db!!.userDao().getUserLogin(email ?: "")
        }
    }

    suspend fun logout(navController: NavController) {
        with(sharedPreferences?.edit()) {
            this?.putString("email", "")
            this?.apply()
        }
        navController.navigate(Login.pageTitle) {
            popUpTo(Intro.pageTitle) {
                inclusive = true
            }
        }
    }
}