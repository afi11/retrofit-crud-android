package com.example.pertanian.config

import android.content.Context

class AuthConfig(val context: Context) {

    var dataPreferences : DataPreferences ?= null

    fun userId() : String {
        dataPreferences = DataPreferences(context)
        return dataPreferences!!.getUserId()
    }

    fun roleUser() : String {
        dataPreferences = DataPreferences(context)
        return dataPreferences!!.getUserRole()
    }

    fun cekUserIsLoggin() : Boolean{
        var state : Boolean
        dataPreferences = DataPreferences(context)
        val userid = dataPreferences!!.getUserId()
        val role = dataPreferences!!.getUserRole()
        if(userid != "" && role != "") state = true
        else state = false
        return state
    }

    fun addUserData(userId: String, role: String) : Boolean {
        dataPreferences = DataPreferences(context)
        return dataPreferences!!.addUserData(userId, role)
    }

    fun logoutUser() : Boolean {
        dataPreferences = DataPreferences(context)
        return dataPreferences!!.removeUserData()
    }


}