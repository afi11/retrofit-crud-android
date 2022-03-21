package com.example.pertanian.services

import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianList
import com.example.pertanian.models.PertanianResponse
import retrofit2.Call
import retrofit2.http.*

interface PertanianService {

    // Jagung
    @GET("jagung")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getJagungList(@Query("cari") cari: String): Call<PertanianList>

    @GET("jagung/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getJagungBy(@Path("id") id: String): Call<PertanianResponse>

    @POST("jagung")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createJagung(@Body params: Pertanian): Call<PertanianResponse>

    @PUT("jagung/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateJagung(@Path("id") id: String, @Body params: Pertanian): Call<PertanianResponse>

    @DELETE("jagung/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteJagung(@Path("id") id: String): Call<PertanianResponse>

    // Kacang
    @GET("kacang")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getKacangList(@Query("cari") cari: String): Call<PertanianList>

    @GET("kacang/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getKacangBy(@Path("id") id: String): Call<PertanianResponse>

    @POST("kacang")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createKacang(@Body params: Pertanian): Call<PertanianResponse>

    @PUT("kacang/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateKacang(@Path("id") id: String, @Body params: Pertanian): Call<PertanianResponse>

    @DELETE("kacang/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteKacang(@Path("id") id: String): Call<PertanianResponse>

    // Kedelai
    @GET("kedelai")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getKedelaiList(@Query("cari") cari: String): Call<PertanianList>

    @GET("kedelai/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getKedelaiBy(@Path("id") id: String): Call<PertanianResponse>

    @POST("kedelai")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createKedelai(@Body params: Pertanian): Call<PertanianResponse>

    @PUT("kedelai/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateKedelai(@Path("id") id: String, @Body params: Pertanian): Call<PertanianResponse>

    @DELETE("kedelai/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteKedelai(@Path("id") id: String): Call<PertanianResponse>

    // Padi
    @GET("padi")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getPadiList(@Query("cari") cari: String): Call<PertanianList>

    @GET("padi/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getPadiBy(@Path("id") id: String): Call<PertanianResponse>

    @POST("padi")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createPadi(@Body params: Pertanian): Call<PertanianResponse>

    @PUT("padi/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updatePadi(@Path("id") id: String, @Body params: Pertanian): Call<PertanianResponse>

    @DELETE("padi/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deletePadi(@Path("id") id: String): Call<PertanianResponse>

    // UbiJalar
    @GET("ubijalar")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUbiJalarList(@Query("cari") cari: String): Call<PertanianList>

    @GET("ubijalar/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUbiJalarBy(@Path("id") id: String): Call<PertanianResponse>

    @POST("ubijalar")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createUbiJalar(@Body params: Pertanian): Call<PertanianResponse>

    @PUT("ubijalar/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateUbiJalar(@Path("id") id: String, @Body params: Pertanian): Call<PertanianResponse>

    @DELETE("ubijalar/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteUbiJalar(@Path("id") id: String): Call<PertanianResponse>

    // UbiKayu
    @GET("ubikayu")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUbiKayuList(@Query("cari") cari: String): Call<PertanianList>

    @GET("ubikayu/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getUbiKayuBy(@Path("id") id: String): Call<PertanianResponse>

    @POST("ubikayu")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun createUbiKayu(@Body params: Pertanian): Call<PertanianResponse>

    @PUT("ubikayu/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun updateUbiKayu(@Path("id") id: String, @Body params: Pertanian): Call<PertanianResponse>

    @DELETE("ubikayu/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun deleteUbiKayu(@Path("id") id: String): Call<PertanianResponse>


}