package com.klimgroup.todolist.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.klimgroup.todolist.Others.TaskItems

class DbManager(context: Context) {
    private val dbHelper = DbHelper(context)
    private var db : SQLiteDatabase? = null

    fun openDb(){ db = dbHelper.writableDatabase }
    fun closeDb(){ db?.close() }

    fun insertTask(task:TaskItems){
        DbNames.apply {
            val values = ContentValues().apply {
                put(COLUMN_NAME_TITLE,task.title)
                put(COLUMN_NAME_DESC,task.desc)
                put(COLUMN_NAME_START_TIME,task.startTime)
                put(COLUMN_NAME_END_TIME,task.endTime)
                put(COLUMN_NAME_CREATED_DATE,task.createdDate)
                put(COLUMN_NAME_EDITED_DATE,task.editedDate)
                put(COLUMN_NAME_PERFORMED_DATE,task.performedDate)
            }
            db?.insert(TABLE_NAME_TASKS,null,values)
        }
    }

    fun getTasks():ArrayList<TaskItems>{
        val tasksList = ArrayList<TaskItems>()
        val cursor = db?.query(DbNames.TABLE_NAME_TASKS,null, null, null, null,null,null)
        while (cursor?.moveToNext()!!){
            DbNames.apply {
                val task = TaskItems()
                var index = cursor.getColumnIndex(COLUMN_NAME_TITLE)
                task.title = cursor.getString(index)
                index = cursor.getColumnIndex(COLUMN_NAME_DESC)
                task.desc = cursor.getString(index)
                index = cursor.getColumnIndex(COLUMN_NAME_START_TIME)
                task.startTime = cursor.getString(index)
                index = cursor.getColumnIndex(COLUMN_NAME_END_TIME)
                task.endTime = cursor.getString(index)
                index = cursor.getColumnIndex(COLUMN_NAME_CREATED_DATE)
                task.createdDate = cursor.getString(index)
                index = cursor.getColumnIndex(COLUMN_NAME_EDITED_DATE)
                task.editedDate = cursor.getString(index)
                index = cursor.getColumnIndex(COLUMN_NAME_PERFORMED_DATE)
                task.performedDate = cursor.getString(index)
                index = cursor.getColumnIndex(BaseColumns._ID)
                task.id = cursor.getInt(index)
                tasksList.add(task)
            }
        }
        cursor.close()
        return tasksList
    }

    fun updateTask(task:TaskItems){
        val selection = "${BaseColumns._ID} = ${task.id}"
        DbNames.apply {
            val values = ContentValues().apply {
                put(COLUMN_NAME_TITLE,task.title)
                put(COLUMN_NAME_DESC,task.desc)
                put(COLUMN_NAME_START_TIME,task.startTime)
                put(COLUMN_NAME_END_TIME,task.endTime)
                put(COLUMN_NAME_CREATED_DATE,task.createdDate)
                put(COLUMN_NAME_EDITED_DATE,task.editedDate)
                put(COLUMN_NAME_PERFORMED_DATE,task.performedDate)
            }
            db?.update(TABLE_NAME_TASKS,values,selection,null)
        }
    }

    fun deleteTask(id:Int){
        val selection = "${BaseColumns._ID} = $id"
        db?.delete(DbNames.TABLE_NAME_TASKS,selection,null)
    }
}