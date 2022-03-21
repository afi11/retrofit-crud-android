package com.example.pertanian.services

import com.example.pertanian.models.Auth
import com.example.pertanian.models.AuthResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthService {

    @POST("login")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun postLogin(@Body params: Auth): Call<AuthResponse>

    @GET("getuser/{id}")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getAuthById(@Path("id") id: String): Call<AuthResponse>

}