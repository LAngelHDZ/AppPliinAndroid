package com.example.pliin.apppliin.core.di

import android.content.Context
import androidx.room.Room
import com.example.pliin.apppliin.data.database.PliinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    const val PLIIN_DATABASE_NAME = "pliin_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PliinDatabase::class.java, PLIIN_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUserDao(db: PliinDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun provideTokenDao(db: PliinDatabase) = db.getTokenDao()

    @Singleton
    @Provides
    fun provideDataEmployeeDao(db: PliinDatabase) = db.getDataEmployeeDao()

    @Singleton
    @Provides
    fun provideManifestDao(db: PliinDatabase) = db.getDataManifestDao()

    @Singleton
    @Provides
    fun provideGuideDao(db: PliinDatabase) = db.getGuideDao()
}