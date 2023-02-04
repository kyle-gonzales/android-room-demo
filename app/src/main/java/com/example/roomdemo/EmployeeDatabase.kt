package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDAO

    companion object { // allows us to add functions on the employee database

        /*will keep a reference to any database returned via geInstance()
        * so we don't have to repeatedly initialize the database, which is a performance intensive process*/
        @Volatile // check episode 171
        private var INSTANCE : EmployeeDatabase? = null

        /*helper function to get the database -> singleton pattern
        * only one instance of the database should exist
        * */
        fun getInstance(context: Context) : EmployeeDatabase {
            synchronized(this) {// multiple threads can ask for the database at the same time. we ensure that we initialize the database only once by using a synchronized function; only one thread may enter a synchronized block at a time
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

// version => if we were to update our database (adding properties), we would have to change the version and then do data migration