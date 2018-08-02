package com.diazmain.obapp.room

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Provides
import javax.inject.Singleton

class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) : AppRoomDatabase {
        return Room.databaseBuilder(
                context,
                AppRoomDatabase::class.java,
                "myDB.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db : AppRoomDatabase) : UserDAO {
        return db.userDAO()
    }

    @Singleton
    @Provides
    fun provideReminderDao(db : AppRoomDatabase) : ReminderDAO {
        return db.reminderDAO()
    }

}