package com.example.demofirebaseapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_pins.view.*

class PinAdapter(private val context: Context, private val pins: List<Pin>)
    : RecyclerView.Adapter<PinAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var img = "sazia"
        fun bind(pin: Pin) {
            itemView.tvName.text = pin.name
            itemView.tvLikes.text = " Likes :${pin.likes}"
            Glide.with(context).load(pin.pinUrl).into(itemView.ivPin)
            img = img.replace("sazia",pin.pinUrl)
        }

        init {
            itemView.setOnClickListener { v: View ->
                val intent = Intent(itemView.context, ImageActivity::class.java)
                intent.putExtra("extra_image", img)
                itemView.context.startActivity(intent)
            }
        }
    }

    // Usually involved inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pins, parent, false)
        return ViewHolder(view)
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pin = pins[position]
        holder.bind(pin)
        /*holder.itemView.setOnClickListener {
            Toast.makeText(context,""+pin.name,Toast.LENGTH_SHORT).show()

        }*/
    }
    // returns count of items in the list
    override fun getItemCount(): Int {
        return pins.size
    }
}