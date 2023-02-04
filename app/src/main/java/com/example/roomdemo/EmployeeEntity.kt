package com.example.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee-table") //tableName -> name for the table in the database
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) // creates a unique key for every entry in the table; automatically increments the id
    val id : Int = 0,
    @ColumnInfo(name = "name") // the name of the column in the database MAY differ from the name in the data class; optional
    val name: String = "",
    @ColumnInfo(name = "email-id") // if you choose not to override the column info, by default, the name of the column will be the name of the property in the class
    val email: String = "",
)
