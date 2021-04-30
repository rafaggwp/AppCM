package com.example.appcm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appcm.api.EndPoints
import com.example.appcm.api.LoginPost
import com.example.appcm.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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




            val name = edit_name.text.toString()
            val password = edit_password.text.toString()

            if(name.isEmpty()){
                edit_name.error = getString(R.string.usernamecheck)
                edit_name.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                edit_password.error = getString(R.string.passwordcheck)
                edit_password.requestFocus()
                return@setOnClickListener
            }


            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.postTest(name, password)


            call.enqueue(object: Callback<LoginPost> {
                override fun onResponse(call: Call<LoginPost>, response: Response<LoginPost>) {

                    if (response.isSuccessful){
                        val c: LoginPost = response.body()!!
                        if(!c.status){
                            Toast.makeText(this@Login, "erro", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@Login, "Bem-vindo", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@Login, MapActivity::class.java)
                            startActivity(intent)
                        }

                    }
                }

                override fun onFailure(call: Call<LoginPost>, t: Throwable) {

                }
            })




        }

    }




}

