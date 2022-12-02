package com.klimgroup.todolist

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class TaskItemRepository(private val taskItemDao: TaskItemDao) {
    val allTAskItems: Flow<TaskItem> = taskItemDao.allTasks()

    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem){
        taskItemDao.insertTaskItem(taskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem){
        taskItemDao.updateTaskItem(taskItem)
    }
}