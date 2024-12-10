package com.example.kyrs.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = Worker::class,
            parentColumns = ["id"],
            childColumns = ["workerId"]
        ),
        ForeignKey(
            entity = Employer::class,
            parentColumns = ["id"],
            childColumns = ["employerId"]
        )
    ]
)
data class Payment (
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    val workerId: Int,
    val employerId: Int,
    ////
    val hoursWorked: Double? = null, // для почасового тарифа
    val piecesProduced: Int? = null, // для сдельного тарифа
    val totalSalary: Double, // итоговая зарплата
    ////

    @ColumnInfo(name = "sum")
    var O_sum:Int,

)