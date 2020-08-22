package com.developer.chithlal.taskbubble.ui.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.chithlal.taskbubble.databinding.CardTaskBinding

class TasklistAdapter(val appList:List<Task>): RecyclerView.Adapter<TasklistAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TasklistAdapter.TaskViewHolder {
       val mBinding = CardTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun onBindViewHolder(holder: TasklistAdapter.TaskViewHolder, position: Int) {
        val task = appList[position]
       holder.binding.tvAppName.text = task.appName
    }
    class TaskViewHolder(val binding:CardTaskBinding):RecyclerView.ViewHolder(binding.root)  {

    }

}