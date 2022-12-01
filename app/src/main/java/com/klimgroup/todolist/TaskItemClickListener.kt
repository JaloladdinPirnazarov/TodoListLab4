package com.klimgroup.todolist

interface TaskItemClickListener {
    fun completeTaskItem(taskItem: TaskItem)
    fun editTaskItem(taskItem: TaskItem)
}