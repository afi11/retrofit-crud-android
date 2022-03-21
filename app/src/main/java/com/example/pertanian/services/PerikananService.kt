package com.example.pertanian.services

import com.example.pertanian.models.*
import retrofit2.Call
import retrofit2.http.*

interface PerikananService {

    @GET("perikanan")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getPerikananList(@Query("cari") cari: String): Call<PerikananList>

    @GET("perikanan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getPerikananBy(@Path("id") id: String): Call<PerikananResponse>

    @POST("perikanan")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createPerikanan(@Body params: Perikanan): Call<PerikananResponse>

    @PUT("perikanan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updatePerikanan(@Path("id") id: String, @Body params: Perikanan): Call<PerikananResponse>

    @DELETE("perikanan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deletePerikanan(@Path("id") id: String): Call<PerikananResponse>

}