package com.example.appcm.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/nomes")
    fun getUsers(): Call<List<User>>

    @GET("/nomes/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @FormUrlEncoded
    @POST("/postarverifica")
    fun postTest
            (@Field("name") name: String,
             @Field("password") password: String ?): Call<LoginResponse>
}
