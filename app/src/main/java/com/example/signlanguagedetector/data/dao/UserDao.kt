package com.example.signlanguagedetector.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.signlanguagedetector.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun register(user: User)

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserLogin(email: String): User?

    @Update
    suspend fun update(user: User)

}