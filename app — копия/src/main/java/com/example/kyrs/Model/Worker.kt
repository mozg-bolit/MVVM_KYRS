package com.example.kyrs.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "workers")
data class Worker (
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,


    @ColumnInfo(name = "lastname")
    var W_lastName: String,

    @ColumnInfo(name = "name")
    var W_Name:String,

    @ColumnInfo(name = "middlename")
    var W_middleName:String,
)