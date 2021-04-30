package com.example.appcm.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    //post para login
    @FormUrlEncoded
    @POST("postarverifica")
    fun postTest
            (@Field("name") name: String,
             @Field("password") password: String ?): Call<LoginPost>
    //get para id
    @GET("recebeid/{name}")
    fun getIdByUser(@Path("email") email: String): Call<User>
    //get para markers
    @GET("marker")
    fun getMarker(): Call<List<Marker>>

}
