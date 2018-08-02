package com.diazmain.obapp.room

import android.arch.persistence.room.*
import com.diazmain.obapp.reminder.pojo.Recordatorio
import org.jetbrains.annotations.NotNull

@Entity(tableName = "reminders", indices = arrayOf(Index(value = arrayOf("fecha"), unique = true)) )
data class ReminderEntity (
        @ColumnInfo(name = "fecha")
        @NotNull
        var fecha: String,

        @ColumnInfo(name = "idUser")
        var idUser: Int,

        @Embedded(prefix = "rem_")
        var reminder: Recordatorio?
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}