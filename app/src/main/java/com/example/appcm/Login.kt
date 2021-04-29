package com.example.appcm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.appcm.api.LoginResponse
import com.example.appcm.api.ServiceBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_login.*
import javax.security.auth.callback.Callback

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


       val button = findViewById<Button>(R.id.btn_frag)
        button.setOnClickListener {
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }

       val button1 = findViewById<Button>(R.id.btn_login)
        button1.setOnClickListener {


            val intent = Intent(this@Login, MapActivity::class.java)

            val name = edit_name.text.toString().trim()
            val password = edit_password.text.toString().trim()

            if(name.isEmpty()){
                edit_name.error = "123"
                edit_name.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                edit_password.error = "123"
                edit_password.requestFocus()
                return@setOnClickListener
            }

         /*   ServiceBuilder.instance.postTest(name, password).
                enqueue(object: Callback<LoginResponse>{

                })*/

            startActivity(intent)

        }

    }



}