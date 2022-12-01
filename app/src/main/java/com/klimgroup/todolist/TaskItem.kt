package com.klimgroup.todolist

import android.content.Context
import androidx.core.content.ContextCompat
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskItem (
    var name:String,
    var desc:String,
    var dueTime:LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
        ){
    fun isCompleted() = completedDate != null
    fun imageResource():Int = if (isCompleted()) R.drawable.ic_checked else R.drawable.ic_unchecked
    fun imageColor(context: Context):Int{
        return if(isCompleted()) ContextCompat.getColor(context,R.color.purple_500)
        else ContextCompat.getColor(context,R.color.black)
    }
}