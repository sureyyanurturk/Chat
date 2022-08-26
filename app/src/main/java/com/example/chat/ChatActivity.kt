package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.Adapters.AdapterRecyclerMsg
import com.example.chat.Adapters.AdapterRecyclerUsers
import com.example.chat.Models.ModelMessage
import com.example.chat.databinding.ActivityChatBinding
import com.google.firebase.database.*
import java.util.HashMap




class ChatActivity : AppCompatActivity() {

    private lateinit var chatActivityLayout: ActivityChatBinding
    lateinit var firebaseDataBase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var userName : String
    lateinit var otherName : String
    lateinit var chatAdapter : AdapterRecyclerMsg

    var arrayMsg: MutableList<ModelMessage> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatActivityLayout= DataBindingUtil.setContentView(this,R.layout.activity_chat)

        tanimla()
        chatActivityLayout.chatSendBtn.setOnClickListener(View.OnClickListener {
            sendMsg(chatActivityLayout.chatEditMsg.text.toString())

        })

        loadMsg()



    }

    fun tanimla(){
        userName= intent.extras!!.getString("userName").toString()
        otherName= intent.extras!!.getString("otherName").toString()

        firebaseDataBase= FirebaseDatabase.getInstance()
        databaseReference= firebaseDataBase.reference

        chatActivityLayout.chatUserName.text=otherName

        chatActivityLayout.chatRecycler.layoutManager= LinearLayoutManager(this)

        chatAdapter= AdapterRecyclerMsg( arrayMsg,this@ChatActivity, userName)
        chatActivityLayout.chatRecycler.adapter= chatAdapter



    }

    fun chatBackBtn(view: android.view.View) {
        val intent= Intent(this,MainActivity::class.java)
        intent.putExtra("kAdi",userName)
        startActivity(intent)
    }

    fun sendMsg(text: String){

        if(chatActivityLayout.chatEditMsg.text.isNotEmpty()){
            val key : String = databaseReference.child("Messages").child(userName).child(otherName).push().key!!
            val messageMap: MutableMap<String,Any> = HashMap<String,Any>()
            messageMap["text"] = text
            messageMap["from"] = userName
            databaseReference.child("Messages").child(userName).child(otherName).child(key).setValue(messageMap).addOnCompleteListener {
                if(it.isSuccessful){
                    databaseReference.child("Messages").child(otherName).child(userName).child(key).setValue(messageMap).addOnCompleteListener {

                    }
                }
            }
            chatActivityLayout.chatEditMsg.setText("")
        }



    }

    fun loadMsg(){
        databaseReference.child("Messages").child(userName).child(otherName).addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val mesajModel : ModelMessage? = snapshot.getValue(ModelMessage::class.java)
                arrayMsg.add(mesajModel!!)
                chatAdapter.notifyDataSetChanged()
                chatActivityLayout.chatRecycler.scrollToPosition(arrayMsg.size-1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}