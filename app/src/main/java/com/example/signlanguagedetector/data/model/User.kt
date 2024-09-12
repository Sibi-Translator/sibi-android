package com.example.signlanguagedetector.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val name: String? = null,
    val gender: String? = null,
    val userType: String? = null
)