package com.example.pertanian.viewmodels.kacang_tanah

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PertanianList
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniKacangViewModel: ViewModel() {

    lateinit var kacangDataList: MutableLiveData<PertanianList>

    init {
        kacangDataList = MutableLiveData()
    }

    fun getkacangLiveObserveable(): MutableLiveData<PertanianList> {
        return kacangDataList
    }

    fun getkacangList(cari: String) {
        val kacangInstace = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = kacangInstace.getKacangList(cari)
        call.enqueue(object : Callback<PertanianList>{
            override fun onResponse(call: Call<PertanianList>, response: Response<PertanianList>) {
                if(response.isSuccessful) {
                    kacangDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PertanianList>, t: Throwable) {
                kacangDataList.postValue(null)
            }

        })
    }

}