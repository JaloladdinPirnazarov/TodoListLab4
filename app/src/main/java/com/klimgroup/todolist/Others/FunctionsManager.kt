package com.klimgroup.todolist.Others

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.klimgroup.todolist.R
import java.text.SimpleDateFormat
import java.util.*

class FunctionsManager {

    fun toastMessage(message:String,context: Context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun getDate() : String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat(Constants.DATE_TIME_PATTERN, Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun alertDialog(icon:Int,title:String,message:String,context: Context){
        AlertDialog.Builder(context).apply {
            setIcon(icon)
            setTitle("    $title")
            setMessage(message)
            setPositiveButton("ok"){_,_->}
            show()
        }
    }

    fun showTask(task:TaskItems,context: Context){
        val icon = R.drawable.ic_tasks
        val title = task.title
        var message = "\n"
        if (task.startTime.isNotEmpty()) message += "Start time: ${task.startTime}"
        if (task.endTime.isNotEmpty()) message += "\nEnd time: ${task.endTime}"
        if (task.desc.isNotEmpty()) message += "\n\nDescription: ${task.desc}"
        message += "\n"
        if (task.createdDate.isNotEmpty()) message += "\nCreated date: ${task.createdDate}"
        if (task.editedDate.isNotEmpty()) message += "\nEdited date: ${task.editedDate}"
        if (task.performedDate.isNotEmpty()) message += "\nPerformed date: ${task.performedDate}"
        alertDialog(icon,title,message,context)
    }
}