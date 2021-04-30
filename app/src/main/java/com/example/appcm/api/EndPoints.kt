package com.example.appcm.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EndPoints {

    @FormUrlEncoded
    @POST("postarverifica")
    fun postTest
            (@Field("name") name: String,
             @Field("password") password: String ?): Call<LoginPost>


}
