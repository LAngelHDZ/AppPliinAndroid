package com.example.pliin.apppliin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pliin.apppliin.data.database.dao.DataDao
import com.example.pliin.apppliin.data.database.dao.UserDao
import com.example.pliin.apppliin.data.database.dataconverters.DateConverter
import com.example.pliin.apppliin.data.database.entities.GuideEntity
import com.example.pliin.apppliin.data.database.entities.ManifestEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        GuideEntity::class,
        ManifestEntity::class
    ], version = 1
)
@TypeConverters(DateConverter::class)
abstract class PliinDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getDataDao(): DataDao
}