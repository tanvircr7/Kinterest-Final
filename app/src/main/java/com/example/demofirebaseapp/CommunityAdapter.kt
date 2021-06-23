package com.example.demofirebaseapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_firebase_layout.view.*
import kotlinx.android.synthetic.main.item_pins.view.*
import kotlinx.android.synthetic.main.rv_community_item.view.*

class CommunityAdapter(private val context: Context, var list: ArrayList<User>): RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.userCommunityName.text = user.getUserName()
            itemView.userCommunityEmail.text = user.getUserEmail()
            //var chromeBtn = itemView.chromeEmailBtn
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_community_item,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.name.text = list[position].getUserName()
//        holder.email.text = list[position].getUserEmail()
//        holder.chromeBtn.setOnClickListener {
//            GotoChrome(list[position].getUserEmail())
//        }
        var user = User(); user.setUserName(list[position].getUserName()); user.setUserEmail(list[position].getUserEmail());
        holder.bind(user)
    }

    private fun GotoChrome(userEmail: String) {
        Log.w("ADAPTER","GOto CHrome")
    }

    override fun getItemCount(): Int {
        return list.size
    }


//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val upload = uploadList[position]
//        holder.bind(upload)
//    }


}