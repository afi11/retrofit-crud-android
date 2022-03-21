package com.example.pertanian.viewmodels.peternakan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Peternakan.services.PeternakanService
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PeternakanList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniPeternakanViewModel: ViewModel() {

    lateinit var peternakanDataList: MutableLiveData<PeternakanList>

    init {
        peternakanDataList = MutableLiveData()
    }

    fun getPeternakanLiveObserveable(): MutableLiveData<PeternakanList> {
        return peternakanDataList
    }

    fun getPeternakanList(cari: String) {
        val kacangInstace = RetroInstance.getRetroInstance().create(PeternakanService::class.java)
        val call = kacangInstace.getTernakList(cari)
        call.enqueue(object : Callback<PeternakanList>{
            override fun onResponse(call: Call<PeternakanList>, response: Response<PeternakanList>) {
                Log.d("data",response.body().toString())
                if(response.isSuccessful) {
                    peternakanDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PeternakanList>, t: Throwable) {
                peternakanDataList.postValue(null)
            }

        })
    }

}