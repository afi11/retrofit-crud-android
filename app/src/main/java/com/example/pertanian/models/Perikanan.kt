package com.example.pertanian.models

data class PerikananList(val data: List<Perikanan>)
data class Perikanan(val id: String?, val kecamatan: String?, val perairan_umum: String?, val budidaya_kolam: String?,
                      val pembenihan: String?, val lokasi_lat: String?, val lokasi_long: String?, val created_at: String)
data class PerikananResponse(val code: Int?, val meta: String?, val data: Perikanan?)