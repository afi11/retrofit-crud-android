package com.example.pertanian.models

data class PertanianList(val data: List<Pertanian>)
data class Pertanian(val id: String?, val kecamatan: String?, val produksi: String?, val luas_panen: String?,
                     val rata_rata: String?,
                     val lokasi_lat: String?, val lokasi_long: String?, val created_at: String)
data class PertanianResponse(val code: Int?, val meta: String?, val data: Pertanian?)