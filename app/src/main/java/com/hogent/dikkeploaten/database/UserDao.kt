package com.hogent.dikkeploaten.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user_table ORDER BY userId DESC LIMIT 1")
    fun getUser(): User?

    @Query("DELETE FROM user_table")
    fun deleteUser()

}