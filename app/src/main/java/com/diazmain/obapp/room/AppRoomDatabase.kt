package com.diazmain.obapp.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.diazmain.obapp.room.converters.AlimentosConverter
import com.diazmain.obapp.room.converters.GenericConverter

@Database(entities = arrayOf(ReminderEntity::class, UserEntity::class), version = 4)
@TypeConverters(GenericConverter::class, AlimentosConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun reminderDAO(): ReminderDAO
    abstract fun userDAO(): UserDAO
}