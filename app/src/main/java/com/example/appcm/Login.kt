package com.example.appcm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appcm.api.EndPoints
import com.example.appcm.api.LoginPost
import com.example.appcm.api.ServiceBuilder
import com.example.appcm.api.User

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

        val sharedPrefs: SharedPreferences =
                getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE)

        //VERIFY IF IS LOGGED IN
        val isLoggedIn = sharedPrefs.getBoolean(getString(R.string.pref_is_user_login), false)

        //GET USERNAME SAVED ON SHAREDPREFERENCES
        val usernameSaved = sharedPrefs.getString(getString(R.string.pref_name), "")

        //GET USER ID SAVED ON SHAREDPREFERENCES
        val userId = sharedPrefs.getInt(getString(R.string.pref_user_id), 0)


        if (isLoggedIn) {
            Toast.makeText(this@Login, "$usernameSaved", Toast.LENGTH_SHORT).show()
            val intent = Intent(
                    this@Login,
                    MapActivity::class.java
            )
            intent.putExtra(EXTRA_USERID, userId);
            startActivity(intent)
            finish()
        }

        val button1 = findViewById<Button>(R.id.btn_login)

        button1.setOnClickListener {

            val name = edit_name.text.toString()
            val password = edit_password.text.toString()
            if (name.isEmpty()) {
                edit_name.error = getString(R.string.usernamecheck)
                edit_name.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                edit_password.error = getString(R.string.passwordcheck)
                edit_password.requestFocus()
                return@setOnClickListener
            }


            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.postTest(name, password)


            call.enqueue(object : Callback<LoginPost> {
                override fun onResponse(call: Call<LoginPost>, response: Response<LoginPost>) {

                    if (response.isSuccessful) {
                        val c: LoginPost = response.body()!!
                        if (!c.status) {
                            Toast.makeText(this@Login, "erro", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Login, "Bem-vindo", Toast.LENGTH_SHORT).show()

                            val request2 =
                                    ServiceBuilder.buildService(EndPoints::class.java)
                            val getUsersByName = request2.getUsersByName(
                                    edit_name.text.toString()
                            )

                            getUsersByName.enqueue(object : retrofit2.Callback<User> {
                                override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                ) {
                                    if (response.isSuccessful) {


                                        val user: User = response.body()!!
                                        val editor = sharedPrefs.edit()
                                        editor.putBoolean(
                                                getString(R.string.pref_is_user_login),
                                                true
                                        )
                                        editor.putString(
                                                getString(R.string.pref_name),
                                                edit_name.text.toString()
                                        )
                                        editor.putInt(
                                                getString(R.string.pref_user_id),
                                                user.id
                                        )
                                        editor.apply()

                                        val intent = Intent(
                                                this@Login,
                                                MapActivity::class.java
                                        )

                                        intent.putExtra(EXTRA_USERID, user.id);
                                        startActivity(intent)
                                        finish()


                                    }
                                }

                                override fun onFailure(call: Call<User>, t: Throwable) {
                                    Toast.makeText(
                                            this@Login,
                                            R.string.errosp,
                                            Toast.LENGTH_SHORT
                                    )
                                            .show()
                                }

                            })

                        }


                    }

                }

                override fun onFailure(call: Call<LoginPost>, t: Throwable) {
                    TODO("Not yet implemented")
                }


                })



        }


    }

    companion object {
        const val EXTRA_USERID = "com.estg.fixity.messages.USERID"
    }
}






