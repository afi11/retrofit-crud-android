package com.example.pertanian.config

import android.content.Context
import android.content.SharedPreferences

class DataPreferences(val context: Context) {

    val APPNAME = "BPSAPP"
    val SET_USER_ID = "SET_USER_ID"
    val SET_USER_ROLE = "SET_USER_ROLE"

    val sharedRef : SharedPreferences = context.getSharedPreferences(APPNAME, Context.MODE_PRIVATE)

    fun addUserData(idUser: String, role: String) : Boolean {
        val editor_userid : SharedPreferences.Editor = sharedRef.edit()
        val editor_role : SharedPreferences.Editor = sharedRef.edit()
        editor_userid.putString(SET_USER_ID, idUser)
        editor_role.putString(SET_USER_ROLE, role)
        editor_userid.commit()
        editor_role.commit()
        return true
    }

    fun removeUserData(): Boolean {
        val editor_userid : SharedPreferences.Editor = sharedRef.edit()
        val editor_role : SharedPreferences.Editor = sharedRef.edit()
        editor_userid.putString(SET_USER_ID, "")
        editor_role.putString(SET_USER_ROLE, "")
        editor_userid.commit()
        editor_role.commit()
        return true
    }

    fun getUserId() : String {
        return sharedRef.getString(SET_USER_ID,"").toString()
    }

    fun getUserRole() : String {
        return sharedRef.getString(SET_USER_ROLE, "").toString()
    }
}