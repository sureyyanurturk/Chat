package com.example.chat.Adapters

import android.app.Activity
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.ChatActivity
import com.example.chat.MainActivity
import com.example.chat.R
import com.google.firebase.database.core.Context

class AdapterRecyclerUsers(var list: MutableList<String>,var context: MainActivity,var userName : String) : RecyclerView.Adapter<AdapterRecyclerUsers.MainViewHolder>() {


    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById(R.id.userName)
        var usersMainLayout: ConstraintLayout = itemView.findViewById(R.id.usersMainLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_users, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.userName.text = list[position]
        holder.usersMainLayout.setOnClickListener(View.OnClickListener {
            val intent= Intent(context,ChatActivity::class.java)
            intent.putExtra("userName", userName)
            intent.putExtra("otherName", list[position])
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
       return list.size
    }
}