package com.example.mytestroom.domain

import com.example.mytestroom.data.models.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(userData: UserData)

    suspend fun updateUser(userData: UserData)

    suspend fun deleteUser(userData: UserData)

    fun getUsers(): Flow<List<UserData>>
}