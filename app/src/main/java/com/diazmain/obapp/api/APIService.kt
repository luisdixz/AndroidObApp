package com.diazmain.obapp.api

import com.diazmain.obapp.Home.model.MeasuresResult
import com.diazmain.obapp.Login.model.Result
import com.diazmain.obapp.Reminder.Pojo.Recordatorio
import retrofit2.Call
import retrofit2.http.*

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
    @POST("addReminder")
    fun addReminder(
            @Body reco: Recordatorio
    ): Call<Result>

    @GET("measures/getAll/{id}")
    fun getAllMeasures(
            @Path("id") id: Int
    ): Call<MeasuresResult>

    //TODO éstos request no se han implementado en el proyecto
    @GET("measures/get/{id},{month}")
    fun getLastMeasures(
            @Path("id") id: Int,
            @Path("month") month: Int
    ): Call<MeasuresResult>


}
