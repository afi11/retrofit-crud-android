package com.example.pertanian.utils

class Tanggal {

    companion object {
        fun getOnlyYear(dateTime: String) : String {
            return dateTime.substring(0,4)
        }

        fun getTanggal(dateTime: String) : String {
            return dateTime.substring(0,10)
        }

    }


}