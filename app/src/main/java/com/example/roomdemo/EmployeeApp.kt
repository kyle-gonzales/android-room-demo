package com.example.roomdemo

import android.app.Application



// Application must be declared in manifest -> android:name=".EmployeeApp"
class EmployeeApp : Application(){

    /*
    * creates the database lazily:
    *   it loads the needed value to our variable whenever it is needed, not directly
    * */

    val db by lazy {
        EmployeeDatabase.getInstance(this) // initialize the database
    }

}