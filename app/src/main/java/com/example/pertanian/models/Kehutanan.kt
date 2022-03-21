package com.example.pertanian.models

data class KehutananList(val data: List<Kehutanan>)
data class Kehutanan(val id: String?, val tanaman_hutan: String?, val produksi: String?,
                     val nilai_produksi: String?, val lokasi_lat: String?, val lokasi_long: String?, val created_at: String)
data class KehutananResponse(val code: Int?, val meta: String?, val data: Kehutanan?)