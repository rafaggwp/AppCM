package com.example.appcm.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @FormUrlEncoded
    @POST("postarverifica")
    fun postTest
            (@Field("name") name: String,
             @Field("password") password: String ?): Call<LoginPost>

    @GET("recebeid/{name}")
    fun getIdByUser(@Path("email") email: String): Call<User>

    @GET("marker")
    fun getMarker(): Call<List<Marker>>

}
