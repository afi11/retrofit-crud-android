package com.example.pertanian.viewmodels.ubijalar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PertanianList
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniUbiJalarViewModel: ViewModel() {

    lateinit var ubiJalarDataList: MutableLiveData<PertanianList>

    init {
        ubiJalarDataList = MutableLiveData()
    }

    fun getUbiJalarLiveObserveable(): MutableLiveData<PertanianList> {
        return ubiJalarDataList
    }

    fun getUbiJalarList(cari: String) {
        val kacangInstace = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = kacangInstace.getUbiJalarList(cari)
        call.enqueue(object : Callback<PertanianList>{
            override fun onResponse(call: Call<PertanianList>, response: Response<PertanianList>) {
                if(response.isSuccessful) {
                    ubiJalarDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PertanianList>, t: Throwable) {
                ubiJalarDataList.postValue(null)
            }

        })
    }

}