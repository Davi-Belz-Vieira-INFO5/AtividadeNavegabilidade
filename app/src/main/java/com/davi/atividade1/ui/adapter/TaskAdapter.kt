package com.davi.atividade1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davi.atividade1.data.model.Task

import com.davi.atividade1.databinding.ItemTaskBinding

class TaskAdapter(
     private val taskList: List<Task>): RecyclerView.Adapter<TaskAdapter.MyViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
        TODO("Not yet implemented")
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.textDescription.text = task.description
        TODO("Not yet implemented")
    }

    inner class MyViewHolder(val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root){

    }
}
