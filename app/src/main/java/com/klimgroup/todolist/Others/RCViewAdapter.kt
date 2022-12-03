package com.klimgroup.todolist.Others

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.klimgroup.todolist.DataBase.DbManager
import com.klimgroup.todolist.R

class RCViewAdapter(
    private val tasksList:ArrayList<TaskItems>,
    private val dbManager: DbManager,
    private val actions: Actions,
    private val context: Context
):RecyclerView.Adapter<RCViewAdapter.Holder>() {
    private val funcManager = FunctionsManager()
    class Holder(view: View) :RecyclerView.ViewHolder(view) {
        val tvRcTitle : TextView= view.findViewById(R.id.tvRcTitle)
        val tvRcStartTime : TextView= view.findViewById(R.id.tvRcStartTime)
        val tvRcEndTime : TextView= view.findViewById(R.id.tvRcEndTime)
        val imgComplate : ImageButton= view.findViewById(R.id.imgComplate)
        val imgMore : ImageButton= view.findViewById(R.id.imgMore)
        fun setData(task: TaskItems,context: Context){
            tvRcTitle.text = task.title
            Log.d("paintFlag","Normal paint flag: ${tvRcEndTime.paintFlags}")
            if (task.performedDate.isNotEmpty()){
                imgComplate.setImageResource(R.drawable.ic_checked)

                tvRcTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvRcStartTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvRcEndTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                tvRcTitle.setTextColor(ContextCompat.getColor(context,R.color.purple_500))
                tvRcStartTime.setTextColor(ContextCompat.getColor(context,R.color.purple_500))
                tvRcEndTime.setTextColor(ContextCompat.getColor(context,R.color.purple_500))
            }else{
                imgComplate.setImageResource(R.drawable.ic_unchecked)

                tvRcTitle.paintFlags = 1283
                tvRcStartTime.paintFlags = 1283
                tvRcEndTime.paintFlags = 1283

                tvRcTitle.setTextColor(ContextCompat.getColor(context,R.color.black))
                tvRcStartTime.setTextColor(ContextCompat.getColor(context,R.color.black))
                tvRcEndTime.setTextColor(ContextCompat.getColor(context,R.color.black))
            }

            tvRcStartTime.visibility = if (task.startTime.isNotEmpty()){
                tvRcStartTime.text = task.startTime
                View.VISIBLE
            }else View.GONE

            tvRcEndTime.visibility = if (task.endTime.isNotEmpty()){
                tvRcEndTime.text = task.endTime
                View.VISIBLE
            }else View.GONE


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.rc_task_item,parent,false))
    }

    @SuppressLint("DiscouragedPrivateApi")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val task = tasksList[position]
        task.position = position
        holder.setData(task,context)
        holder.itemView.setOnClickListener { funcManager.showTask(task,context) }
        holder.imgMore.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.setOnMenuItemClickListener { item->
                when(item.itemId){

                    R.id.see->{
                        funcManager.showTask(task,context)
                        true
                    }

                    R.id.edit-> {
                        actions.editTask(task)
                        true
                    }

                    R.id.delete->{
                        actions.deleteTask(task.id)
                        true
                    }

                    else -> true
                }
            }

            popupMenu.inflate(R.menu.popup_menu)
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                    .invoke(mPopup,true)
            }catch (e:java.lang.Exception){
                Log.d("PopupMenu","Error showing menu icons: $e")
            }finally {
                popupMenu.show()
            }
        }
        holder.imgComplate.setOnClickListener {
            if (task.performedDate.isNotEmpty()){
                task.performedDate = ""
                dbManager.updateTask(task)

                holder.imgComplate.setImageResource(R.drawable.ic_unchecked)

                holder.tvRcTitle.paintFlags = 1283
                holder.tvRcStartTime.paintFlags = 1283
                holder.tvRcEndTime.paintFlags = 1283

                holder.tvRcTitle.setTextColor(ContextCompat.getColor(context,R.color.black))
                holder.tvRcStartTime.setTextColor(ContextCompat.getColor(context,R.color.black))
                holder.tvRcEndTime.setTextColor(ContextCompat.getColor(context,R.color.black))

                funcManager.toastMessage("Task unperformed",context)
            }else{
                task.performedDate = funcManager.getDate()
                dbManager.updateTask(task)

                holder.imgComplate.setImageResource(R.drawable.ic_checked)

                holder.tvRcTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.tvRcStartTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.tvRcEndTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

                holder.tvRcTitle.setTextColor(ContextCompat.getColor(context,R.color.purple_500))
                holder.tvRcStartTime.setTextColor(ContextCompat.getColor(context,R.color.purple_500))
                holder.tvRcEndTime.setTextColor(ContextCompat.getColor(context,R.color.purple_500))

                funcManager.toastMessage("Task performed",context)
            }
        }
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(newTasksList:ArrayList<TaskItems>){
        tasksList.clear()
        tasksList.addAll(newTasksList)
        notifyDataSetChanged()
    }
}