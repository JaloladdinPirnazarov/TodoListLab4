package com.klimgroup.todolist.DataBase

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context): SQLiteOpenHelper(
    context,DbNames.DATABASE_NAME,null,DbNames.DATABASE_VERSION) {
    @SuppressLint("SQLiteString")
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbNames.CREATE_TABLE_TASKS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbNames.DELETE_TASKS_TABLE)
        onCreate(db)
    }

}