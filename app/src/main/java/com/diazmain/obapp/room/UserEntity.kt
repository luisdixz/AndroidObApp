package com.diazmain.obapp.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (

        @PrimaryKey
        @ColumnInfo(name = "user_id")
        var id: Int = 0,

        @ColumnInfo(name = "name")
        var name: String?,

        @ColumnInfo(name = "lastname")
        var lastName: String?,

        @ColumnInfo(name = "username")
        var username: String?,

        @ColumnInfo(name = "birthdate")
        var birth: String?
) {
}