package com.example.demofirebaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_firebase_layout.view.*
import kotlinx.android.synthetic.main.item_pins.view.*

class MyFirebaseAdapter(private val context: Context, private val uploadList: List<Upload>)
    : RecyclerView.Adapter<MyFirebaseAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        fun bind(upload: Upload) {
            itemView.textFirebaseView.text = upload.getImageName()
            Picasso.with(context).load(upload.getImageUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerInside()
                .into(itemView.cardFirebaseIv)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_firebase_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upload = uploadList[position]
        holder.bind(upload)
    }

    override fun getItemCount(): Int {
        return uploadList.size
    }
}

