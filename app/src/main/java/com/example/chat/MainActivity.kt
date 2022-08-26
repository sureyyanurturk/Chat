package com.example.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.Adapters.AdapterRecyclerUsers
import com.example.chat.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityLayout: ActivityMainBinding

    lateinit var firebaseDataBase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var userAdapter : AdapterRecyclerUsers

    var arrayUsers: MutableList<String> = ArrayList()
    lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityLayout= DataBindingUtil.setContentView(this,R.layout.activity_main)

        tanimla()
        usersList()



    }


    fun tanimla(){
        firebaseDataBase= FirebaseDatabase.getInstance()
        databaseReference= firebaseDataBase.reference

        userName= intent.extras!!.getString("kAdi").toString()

        mainActivityLayout.recyclerViewUser.layoutManager= GridLayoutManager(this,2)
        userAdapter= AdapterRecyclerUsers(arrayUsers, this@MainActivity, userName)
        mainActivityLayout.recyclerViewUser.adapter= userAdapter
    }

    fun usersList(){
        databaseReference.child("Users").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(!snapshot.key.equals(userName)){
                    arrayUsers.add(snapshot.key!!)
                    userAdapter.notifyDataSetChanged()
                }

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


