package com.example.pertanian.viewmodels.padi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PertanianList
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniPadiViewModel: ViewModel() {

    lateinit var padiDataList: MutableLiveData<PertanianList>

    init {
        padiDataList = MutableLiveData()
    }

    fun getPadiLiveObserveable(): MutableLiveData<PertanianList> {
        return padiDataList
    }

    fun getPadiList(cari: String) {
        val kacangInstace = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = kacangInstace.getPadiList(cari)
        call.enqueue(object : Callback<PertanianList>{
            override fun onResponse(call: Call<PertanianList>, response: Response<PertanianList>) {
                if(response.isSuccessful) {
                    padiDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PertanianList>, t: Throwable) {
                padiDataList.postValue(null)
            }

        })
    }

}