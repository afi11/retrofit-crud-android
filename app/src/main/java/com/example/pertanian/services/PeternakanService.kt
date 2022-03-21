package com.example.Peternakan.services

import com.example.pertanian.models.Peternakan
import com.example.pertanian.models.PeternakanList
import com.example.pertanian.models.PeternakanResponse
import retrofit2.Call
import retrofit2.http.*

interface PeternakanService {

    @GET("peternakan")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getTernakList(@Query("cari") cari: String): Call<PeternakanList>

    @GET("peternakan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getTernakBy(@Path("id") id: String): Call<PeternakanResponse>

    @POST("peternakan")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createTernak(@Body params: Peternakan): Call<PeternakanResponse>

    @PUT("peternakan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateTernak(@Path("id") id: String, @Body params: Peternakan): Call<PeternakanResponse>

    @DELETE("peternakan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteTernak(@Path("id") id: String): Call<PeternakanResponse>
    
}