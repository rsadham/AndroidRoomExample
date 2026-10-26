package com.example.mytestroom.data.locals

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mytestroom.data.models.UserEntity


@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}