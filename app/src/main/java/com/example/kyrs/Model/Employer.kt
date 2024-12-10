package com.example.kyrs.Model

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ColumnInfo

@Entity(tableName = "emoloyers")
data class Employer (
    @PrimaryKey (autoGenerate = true)
    var id:Int? = null,

    @ColumnInfo(name = "lastname")
    var E_lastName:String,

    @ColumnInfo(name = "name")
    var E_Name:String,

    @ColumnInfo(name = "middlename")
    var E_middleName:String,
)