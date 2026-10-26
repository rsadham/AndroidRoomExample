package com.example.mytestroom.data

import com.example.mytestroom.data.locals.UserDao
import com.example.mytestroom.data.locals.toDomain
import com.example.mytestroom.data.locals.toEntity
import com.example.mytestroom.data.models.UserData
import com.example.mytestroom.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(userData: UserData) {
        userDao.insertUser(userData.toEntity())
    }

    override suspend fun updateUser(userData: UserData) {
        userDao.updateUser(userData.toEntity())
    }

    override suspend fun deleteUser(userData: UserData) {
        userDao.deleteUser(userData.toEntity())
    }

    override fun getUsers(): Flow<List<UserData>> {
        return userDao.getUsers().map { entities -> entities.map { entity -> entity.toDomain() } }
    }
}