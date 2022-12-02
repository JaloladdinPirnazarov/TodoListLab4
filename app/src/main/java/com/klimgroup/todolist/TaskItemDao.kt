package com.klimgroup.todolist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskItemDao {

    @Query("SELECT * FROM task_item_table ORDER BY ID ASC")
    fun allTasks(): Flow<TaskItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskItem(taskItem: TaskItem)
    suspend fun updateTaskItem(taskItem: TaskItem)
    suspend fun deleteTaskItem(taskItem: TaskItem)
}