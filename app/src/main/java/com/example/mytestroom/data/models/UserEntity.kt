package com.example.mytestroom.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long? =0L,
    val userName: String,
    val userLocation:String
)
