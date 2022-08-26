package com.example.chat.Adapters

import android.app.Activity
import android.content.Intent
import android.text.Layout
import android.util.Log
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
import com.example.chat.Models.ModelMessage
import com.example.chat.R
import com.google.firebase.database.core.Context
import kotlin.properties.Delegates

class AdapterRecyclerMsg(var list: MutableList<ModelMessage>, var context: ChatActivity, var userName : String) : RecyclerView.Adapter<AdapterRecyclerMsg.MainViewHolder>() {

companion object{
    var state : Boolean = false
    var view_send : Int=1
    var view_received : Int=2
}







   class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


         var textView: TextView = if(state)
             itemView.findViewById(R.id.sendText)
         else itemView.findViewById(R.id.receivedText)


   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MainViewHolder{

        val view: View

        return if(viewType == view_send){

            view=  LayoutInflater.from(parent.context).inflate(R.layout.recycler_send, parent, false)
            MainViewHolder(view)
        } else{
            view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_received, parent, false)
            MainViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.textView.text = list[position].text

    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(list[position].equals(userName)){
            state= true
            view_send
        }else{
            state= false
            view_received
        }

    }
}