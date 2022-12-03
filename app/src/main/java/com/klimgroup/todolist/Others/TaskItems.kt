package com.klimgroup.todolist.Others

import android.content.Context
import androidx.core.content.ContextCompat
import com.klimgroup.todolist.R

class TaskItems(
    var title: String = "",
    var desc: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var createdDate: String = "",
    var editedDate: String = "",
    var performedDate: String = "",
    var isEdit: Boolean = false,
    var id: Int = -1,
    var position: Int = -1
){
    fun getColor(context: Context):Int {
        return if (performedDate.isNotEmpty()) ContextCompat.getColor(context,R.color.purple_500)
        else ContextCompat.getColor(context,R.color.black)
    }
}
