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

    @ColumnInfo
    var id:Int? = null,

    @ColumnInfo(name = "workerId")
    val workerId: Int,

    @ColumnInfo(name = "employerId")
    val employerId: Int,
    ////
    @ColumnInfo(name = "hoursWorked")
    val hoursWorked: Double? = null, // для почасового тарифа

    @ColumnInfo(name = "piecesProduced")
    val piecesProduced: Int? = null, // для сдельного тарифа

    @ColumnInfo(name = "totalPayment")
    val totalPayment: Double, // итоговая зарплата
    ////

    @ColumnInfo(name = "sum")
    var O_sum:Int,

)