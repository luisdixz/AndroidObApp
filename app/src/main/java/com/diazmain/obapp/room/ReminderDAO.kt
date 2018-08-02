package com.diazmain.obapp.room

import android.arch.persistence.room.*

@Dao
interface ReminderDAO {

    @Insert( onConflict = OnConflictStrategy.IGNORE )
    fun insert(rem: ReminderEntity) : Long

    @Delete
    fun delete(rem: ReminderEntity)

    // I like this name xd
    @Query("DELETE FROM reminders")
    fun nukeTable()

    @Query("SELECT * FROM reminders WHERE fecha = :date AND idUser = :idUser")
    fun findByDate(date: String, idUser: Long) : ReminderEntity?

    @Query("SELECT * FROM reminders ORDER BY fecha DESC LIMIT 1")
    fun findLast() : ReminderEntity?

    @Query("SELECT count(*) FROM reminders ")
    fun getRows() : Long
}