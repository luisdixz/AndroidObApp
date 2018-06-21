package com.diazmain.obapp.Login.api

import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.Reminder.Pojo.Recordatorio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("register")
    fun createUser(
            @Field("nombre") nombre: String,
            @Field("apellidos") apellidos: String,
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Result>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Result>

    //@FormUrlEncoded
    @POST("reminder/add")
    fun addReminder(
            @Body reco: Recordatorio
    ): Call<Result>
}
