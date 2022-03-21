package com.example.pertanian.viewmodels.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Auth
import com.example.pertanian.models.AuthResponse
import com.example.pertanian.services.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel: ViewModel() {

    lateinit var createAuthLiveData: MutableLiveData<AuthResponse?>
    lateinit var loadAuthData: MutableLiveData<AuthResponse?>

    init {
        createAuthLiveData = MutableLiveData()
        loadAuthData = MutableLiveData()
    }

    fun getCreateAuthLiveDataObservable() : MutableLiveData<AuthResponse?> {
        return createAuthLiveData
    }

    fun getLoadAuthDataObservable() : MutableLiveData<AuthResponse?> {
        return loadAuthData
    }

    fun createAuth(auth: Auth) {
        val retroInstance = RetroInstance.getRetroInstance().create(AuthService::class.java)
        val call = retroInstance.postLogin(auth)
        call.enqueue(object : Callback<AuthResponse?> {
            override fun onResponse(
                call: Call<AuthResponse?>,
                response: Response<AuthResponse?>
            ) {
                if(response.isSuccessful){
                    createAuthLiveData.postValue(response.body())
                    Log.d("dataUser", response.body()!!.data.toString())
                }else{
                    createAuthLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<AuthResponse?>, t: Throwable) {
                createAuthLiveData.postValue(null)
            }

        })
    }

    fun getAuthData(id: String?){
        val retroInstance = RetroInstance.getRetroInstance().create(AuthService::class.java)
        val call = retroInstance.getAuthById(id!!)
        call.enqueue(object : Callback<AuthResponse?> {
            override fun onResponse(
                call: Call<AuthResponse?>,
                response: Response<AuthResponse?>
            ) {
                if(response.isSuccessful){
                    loadAuthData.postValue(response.body())
                }else{
                    loadAuthData.postValue(null)
                }
            }

            override fun onFailure(call: Call<AuthResponse?>, t: Throwable) {
                loadAuthData.postValue(null)
            }

        })
    }
}