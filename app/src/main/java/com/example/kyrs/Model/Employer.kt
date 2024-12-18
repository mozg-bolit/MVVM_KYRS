package com.example.kyrs.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employers")
data class Employer(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "lastname")
    var lastName: String,

    @ColumnInfo(name = "name")
    var firstName: String,

    @ColumnInfo(name = "middlename")
    var middleName: String,

    @ColumnInfo(name = "password")
    var password: String
)
