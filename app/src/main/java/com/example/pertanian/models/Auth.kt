package com.example.pertanian.models

data class AuthList(val data: List<Auth>)
data class Auth(val id: String, val name: String, val email: String, val password: String, val role: String)
data class AuthResponse(val code: Int?, val meta: String?, val data: Auth?)