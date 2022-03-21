package com.example.pertanian.viewmodels.kedelai

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PertanianList
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniKedelaiViewModel: ViewModel() {

    lateinit var kedelaiDataList: MutableLiveData<PertanianList>

    init {
        kedelaiDataList = MutableLiveData()
    }

    fun getKedelaiLiveObserveable(): MutableLiveData<PertanianList> {
        return kedelaiDataList
    }

    fun getKedelaiList(cari: String) {
        val kacangInstace = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = kacangInstace.getKedelaiList(cari)
        call.enqueue(object : Callback<PertanianList>{
            override fun onResponse(call: Call<PertanianList>, response: Response<PertanianList>) {
                if(response.isSuccessful) {
                    kedelaiDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PertanianList>, t: Throwable) {
                kedelaiDataList.postValue(null)
            }

        })
    }

}