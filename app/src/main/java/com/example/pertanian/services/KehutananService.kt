package com.example.pertanian.services

import com.example.pertanian.models.Kehutanan
import com.example.pertanian.models.KehutananList
import com.example.pertanian.models.KehutananResponse
import retrofit2.Call
import retrofit2.http.*

interface KehutananService {

    @GET("kehutanan")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getKehutananList(@Query("cari") cari: String): Call<KehutananList>

    @GET("kehutanan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getKehutananBy(@Path("id") id: String): Call<KehutananResponse>

    @POST("kehutanan")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createKehutanan(@Body params: Kehutanan): Call<KehutananResponse>

    @PUT("kehutanan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateKehutanan(@Path("id") id: String, @Body params: Kehutanan): Call<KehutananResponse>

    @DELETE("kehutanan/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteKehutanan(@Path("id") id: String): Call<KehutananResponse>

}