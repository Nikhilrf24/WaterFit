package com.example.waterfit

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


lateinit var application: Application

class WaterAdapter(private val waterlog : ArrayList<WaterItem>, private val activity: MainActivity)
    : RecyclerView.Adapter<WaterAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val hoursTextView = itemView.findViewById<TextView>(R.id.litres)
        private val dateTextView = itemView.findViewById<TextView>(R.id.date)

        fun bind(sleepItem: WaterItem) {
            hoursTextView.text = sleepItem.litres
            dateTextView.text = sleepItem.date
        }
    }

    fun removeAt(position: Int) {
        val sleepItem = waterlog[position]
        waterlog.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, waterlog.size)
        activity.delete(sleepItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.water_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val log = waterlog[position]
        holder.bind(log)
    }

    override fun getItemCount() = waterlog.size
}
