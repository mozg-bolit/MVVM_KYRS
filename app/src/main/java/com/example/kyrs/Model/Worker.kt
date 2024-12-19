package com.example.kyrs.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workers")
data class Worker(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,  // Изменено с String на Long

    @ColumnInfo(name = "lastname")
    var lastname: String,

    @ColumnInfo(name = "name")
    var firstname: String,

    @ColumnInfo(name = "middlename")
    var middlename: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "rate")
    var rate: Float,

    @ColumnInfo(name = "payment_type")
    val payment_type:String
)
