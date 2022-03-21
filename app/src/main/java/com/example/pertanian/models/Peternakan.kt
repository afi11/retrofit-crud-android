package com.example.pertanian.models

data class PeternakanList(val data: List<Peternakan>)
data class Peternakan(val id: String?, val kecamatan: String?, val sapi_potong: String?, val kambing: String?,
                     val ayam_kampung: String?,
                     val lokasi_lat: String?, val lokasi_long: String?, val created_at: String)
data class PeternakanResponse(val code: Int?, val meta: String?, val data: Peternakan?)