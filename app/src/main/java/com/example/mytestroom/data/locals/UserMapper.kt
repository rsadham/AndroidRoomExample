package com.example.mytestroom.data.locals


import com.example.mytestroom.data.models.UserData
import com.example.mytestroom.data.models.UserEntity


fun UserEntity.toDomain(): UserData {
    return UserData(
        id = id,
        userName = userName,
        userLocation = userLocation
    )
}

fun UserData.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        userName = userName,
        userLocation = userLocation
    )
}