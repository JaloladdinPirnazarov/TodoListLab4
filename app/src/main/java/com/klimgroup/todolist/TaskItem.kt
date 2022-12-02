package com.klimgroup.todolist

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "task_item_table")
class TaskItem (
    @ColumnInfo(name = "name") var name:String,
    @ColumnInfo(name = "desc") var desc:String,
    @ColumnInfo(name = "dueTimeString") var dueTimeString:String?,
    @ColumnInfo(name = "completedDateString") var completedString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
        ){

    @RequiresApi(Build.VERSION_CODES.O)
    fun completedDate(): LocalDate? {
        return if (completedString == null) null
        else LocalDate.parse(completedString, dateFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dueTime():LocalTime? {
        return if (completedString == null) null
        else LocalTime.parse(dueTimeString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isCompleted() = completedDate() != null
    @RequiresApi(Build.VERSION_CODES.O)
    fun imageResource():Int = if (isCompleted()) R.drawable.ic_checked else R.drawable.ic_unchecked
    @RequiresApi(Build.VERSION_CODES.O)
    fun imageColor(context: Context):Int{
        return if(isCompleted()) ContextCompat.getColor(context,R.color.purple_500)
        else ContextCompat.getColor(context,R.color.black)
    }

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}