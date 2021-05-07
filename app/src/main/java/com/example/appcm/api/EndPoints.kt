package com.example.appcm.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    //GET USERS BY USERNAME
    @GET("nomes/{name}")
    fun getUsersByName(@Path("name") name: String): Call<User>


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


    @FormUrlEncoded
    @POST("marker/add")
    fun postMarker(
        @Field("descr") descr: String?,
        @Field("mType") mType: Int?,
        @Field("lat") lat: Double?,
        @Field("lng") lng: Double?,
        @Field("user_id") users_id: Int?
    ): Call<LoginPost>
}
