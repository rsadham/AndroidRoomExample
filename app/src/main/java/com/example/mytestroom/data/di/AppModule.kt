package com.example.mytestroom.data.di

import android.content.Context
import androidx.room.Room
import com.example.mytestroom.data.UserRepoImpl
import com.example.mytestroom.data.locals.AppDatabase
import com.example.mytestroom.data.locals.UserDao
import com.example.mytestroom.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "user_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepoImpl(userDao: UserDao): UserRepository {
        return UserRepoImpl(userDao)
    }
}