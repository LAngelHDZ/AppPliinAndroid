package com.example.pliin.apppliin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pliin.apppliin.data.database.dao.EmployeeDao
import com.example.pliin.apppliin.data.database.dao.GuideDao
import com.example.pliin.apppliin.data.database.dao.ManifestDao
import com.example.pliin.apppliin.data.database.dao.TokenDao
import com.example.pliin.apppliin.data.database.dao.UserDao
import com.example.pliin.apppliin.data.database.dataconverters.DateConverter
import com.example.pliin.apppliin.data.database.entities.*

@Database(
    entities = [
        UserEntity::class,
        GuideEntity::class,
        ManifestEntity::class,
        TokenEntity::class,
        EmployeeEntity::class,
        SessionEntity::class,
    ], version = 1
)
@TypeConverters(DateConverter::class)
abstract class PliinDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getGuideDao(): GuideDao

    abstract fun getTokenDao(): TokenDao

    abstract fun getDataEmployeeDao(): EmployeeDao

    abstract fun getDataManifestDao():ManifestDao
}