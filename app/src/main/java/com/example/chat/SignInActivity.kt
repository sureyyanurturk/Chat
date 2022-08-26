package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chat.databinding.ActivitySignInBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    private lateinit var signInLayout: ActivitySignInBinding
    lateinit var firebaseDataBase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInLayout= DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        tanimla()

    }
    fun tanimla(){
        firebaseDataBase= FirebaseDatabase.getInstance()
        databaseReference= firebaseDataBase.reference
    }

    fun SignInButton(view: android.view.View) {

        if(signInLayout.nameEdit.text.isNotEmpty()){
            ekle(signInLayout.nameEdit.text.toString())
        }
        else{Toast.makeText(this, "Kullanıcı adını giriniz.", Toast.LENGTH_SHORT).show() }




    }

    fun ekle(kAdi: String){

    databaseReference.child("Users").child(kAdi).child("kullaniciadi").setValue(kAdi).addOnCompleteListener {
        if (it.isSuccessful){
            Toast.makeText(this, "Başarılı ile giriş yaptınız.", Toast.LENGTH_SHORT).show()
            val intent= Intent(this@SignInActivity,MainActivity::class.java)
            intent.putExtra("kAdi",kAdi)
            startActivity(intent)
        }
    }



    }


}