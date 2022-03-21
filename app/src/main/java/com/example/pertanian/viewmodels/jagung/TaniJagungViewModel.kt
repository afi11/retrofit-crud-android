package com.example.pertanian.viewmodels.jagung

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PertanianList
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniJagungViewModel: ViewModel() {

    lateinit var jagungDataList: MutableLiveData<PertanianList>

    init {
        jagungDataList = MutableLiveData()
    }

    fun getJagungLiveObserveable(): MutableLiveData<PertanianList> {
        return jagungDataList
    }

    fun getJagungList(cari: String) {
        val jagungInstace = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = jagungInstace.getJagungList(cari)
        call.enqueue(object : Callback<PertanianList>{
            override fun onResponse(call: Call<PertanianList>, response: Response<PertanianList>) {
                if(response.isSuccessful) {
                    jagungDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PertanianList>, t: Throwable) {
                jagungDataList.postValue(null)
            }

        })
    }

}