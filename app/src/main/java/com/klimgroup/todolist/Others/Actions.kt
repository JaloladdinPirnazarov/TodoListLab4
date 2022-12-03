package com.klimgroup.todolist.Others

import android.widget.ImageButton

interface Actions {
    fun getTask(task: TaskItems)
    fun editTask(task: TaskItems)
    fun deleteTask(id:Int)
}