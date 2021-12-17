package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(val listOfItems:List<String>, val longClickLister:OnLongClickListener,val clickLister:ClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }
    interface ClickListener{
        fun onItemClicked(position: Int)
    }
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get the data model based on position
        val item=listOfItems.get(position)
        // Set item views based on your views and data model
        holder.textView.text= item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store refernces to elements in our layout view
        val textView:TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnLongClickListener{
                longClickLister.onItemLongClicked(adapterPosition)
                true
            }
            itemView.setOnClickListener {
                clickLister.onItemClicked(adapterPosition)
                true
            }
        }
    }
}