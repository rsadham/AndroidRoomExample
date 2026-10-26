package com.example.mytestroom.data.locals

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mytestroom.data.models.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getUsers(): Flow<List<UserEntity>>
}