package com.diazmain.obapp.room

import android.arch.persistence.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(us: UserEntity)

    @Delete
    fun delete(us: UserEntity)

    @Query("DELETE FROM users")
    fun nukeTable()

    @Query("SELECT * FROM users WHERE user_id = :id")
    fun findById(id: Int): UserEntity?

    @Query("SELECT * FROM users ORDER BY user_id ASC LIMIT 1")
    fun findLast(): UserEntity?

    @Query("SELECT count(*) FROM users ")
    fun getRows() : Long
}