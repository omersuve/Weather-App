package com.example.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.models.Daily
import com.example.weatherapp.util.Utils
import kotlinx.android.synthetic.main.item_layout.view.*
import java.time.Instant
import java.time.ZoneId

class MyAdapter(private val itemList: List<Daily>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.days
        val imageView: ImageView = itemView.image_view
        val textView: TextView = itemView.text_wtr
        val tempView: TextView = itemView.tv_t
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.imageView.setImageResource(Utils.getImageId(currentItem.weather[0].icon))
        holder.textView.text = currentItem.weather[0].description
        holder.tempView.text = (currentItem.temp.day.toString() + " \u2103")
        holder.day.text = getDateTime(currentItem.dt.toString())
    }

    override fun getItemCount() = itemList.size

    private fun getDateTime(s: String): String {
        val datetime = Instant.ofEpochSecond(s.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

        val str: String = datetime.toString()

        return str.substring(0, 10)
    }
}