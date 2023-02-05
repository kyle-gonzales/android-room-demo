package com.example.roomdemo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
*any functions that use a DAO should be performed in the background thread
*using lifecycleScope.launch{}
*
* */
@Dao
interface EmployeeDAO {
    @Insert
    suspend fun insert(employee:EmployeeEntity)

    @Update
    suspend fun update(employee: EmployeeEntity)

    @Delete
    suspend fun delete(employee: EmployeeEntity)

    @Query(value = "SELECT * FROM `employee-table`") //insert SQL code here
    fun getAllEmployees() : Flow<List<EmployeeEntity>>

    @Query(value = "SELECT * FROM `employee-table` WHERE id=:id") // pass variables to sql code with ':var'
    fun getEmployeeById(id : Int) : Flow<EmployeeEntity>

}

/*
* Flow<Type> -> part of the coroutine class which is used to hold values that can always change at runtime. Always emits a value (similar to a live update)
*  All you need to do is to collect the value from the variable or method without needing to always repeat code to update the user interface
*
* The collect method keeps emitting data as it changes, so you won't have to keep notifying the recycler view that data has changed
*
*  Other ways to listen to a flow:
*   collectLatest -> returns the last value from an update and forgets the previous ones
*   collectIndexed -> get an index of an element with its value
*   combine -> transform flows and return its values automatically when they change
* */