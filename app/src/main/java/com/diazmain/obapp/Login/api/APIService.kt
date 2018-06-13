package com.diazmain.obapp.Login.api

import com.diazmain.obapp.Login.model.Result
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("register")
    fun createUser(
            @Field("name") name: String,
            @Field("lastname") lastname: String,
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Result>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Result>
}