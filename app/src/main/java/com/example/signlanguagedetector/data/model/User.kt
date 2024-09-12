package com.example.signlanguagedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val email: String,
    var password: String,
    var name: String? = null,
    var gender: String? = null,
    var userType: String? = null,
    var image: String? = null
)