package com.example.pertanian.viewmodels.perikanan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PerikananList
import com.example.pertanian.services.PerikananService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniPerikananViewModel: ViewModel() {

    lateinit var perikananDataList: MutableLiveData<PerikananList>

    init {
        perikananDataList = MutableLiveData()
    }

    fun getPerikananLiveObserveable(): MutableLiveData<PerikananList> {
        return perikananDataList
    }


    fun getperikananList(cari: String) {
        val ikanInstace = RetroInstance.getRetroInstance().create(PerikananService::class.java)
        val call = ikanInstace.getPerikananList(cari)
        call.enqueue(object : Callback<PerikananList>{
            override fun onResponse(call: Call<PerikananList>, response: Response<PerikananList>) {
                if(response.isSuccessful) {
                    perikananDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PerikananList>, t: Throwable) {
                perikananDataList.postValue(null)
            }

        })
    }

}