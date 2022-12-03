package com.klimgroup.todolist.DataBase

import android.provider.BaseColumns

object DbNames:BaseColumns {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "MyDb.db"
    const val TABLE_NAME_TASKS = "Tasks"

    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_DESC = "description"
    const val COLUMN_NAME_START_TIME = "start_time"
    const val COLUMN_NAME_END_TIME = "end_time"
    const val COLUMN_NAME_CREATED_DATE = "created_date"
    const val COLUMN_NAME_EDITED_DATE = "edited_date"
    const val COLUMN_NAME_PERFORMED_DATE = "performed_date"

    const val CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS $TABLE_NAME_TASKS (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE STRING, $COLUMN_NAME_DESC STRING," +
            "$COLUMN_NAME_START_TIME STRING, $COLUMN_NAME_END_TIME STRING, " +
            "$COLUMN_NAME_CREATED_DATE STRING, $COLUMN_NAME_EDITED_DATE STRING, " +
            "$COLUMN_NAME_PERFORMED_DATE STRING)"

    const val DELETE_TASKS_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME_TASKS"





}