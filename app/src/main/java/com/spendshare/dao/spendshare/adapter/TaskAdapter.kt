//package com.spendshare.dao.spendshare.adapter
//
//import android.support.v7.widget.AppCompatTextView
//import android.support.v7.widget.RecyclerView
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.spendshare.dao.spendshare.R
//import com.spendshare.dao.spendshare.model.Task
//import org.w3c.dom.Text
//
//import java.lang.reflect.Array
//import java.util.ArrayList
//
//class TasksAdapter(private val tasks: ArrayList<Task>) : RecyclerView.Adapter<TasksAdapter.TaskHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
//        val view = View.inflate(parent.context, R.layout.item_task, parent)
//        return TaskHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
//        holder.taskName.text = tasks[position].taskName
//        holder.status.text = tasks[position].status
//        holder.groupName.text = tasks[position].groupName
//        holder.boxNum.setText(tasks[position].boxNum)
//        holder.progress.maxHeight = tasks[position].progress
//    }
//
//    override fun getItemCount(): Int {
//        return tasks.size
//    }
//
//    inner class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        public val taskName: AppCompatTextView
//        public val status: TextView
//        public val groupName: AppCompatTextView
//        public val boxNum: TextView
//        public val progress: ImageView
//
//        init {
//            taskName = itemView.findViewById(R.id.taskName)
//            status = itemView.findViewById(R.id.status)
//            groupName = itemView.findViewById(R.id.groupName)
//            boxNum = itemView.findViewById(R.id.boxNum)
//            progress = itemView.findViewById(R.id.progress)
//        }
//    }
//}
