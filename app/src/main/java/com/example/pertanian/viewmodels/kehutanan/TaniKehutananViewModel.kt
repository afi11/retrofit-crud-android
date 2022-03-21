package com.example.pertanian.viewmodels.kehutanan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.KehutananList
import com.example.pertanian.services.KehutananService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaniKehutananViewModel: ViewModel() {

    lateinit var kehutananDataList: MutableLiveData<KehutananList>

    init {
        kehutananDataList = MutableLiveData()
    }

    fun getKehutananLiveObserveable(): MutableLiveData<KehutananList> {
        return kehutananDataList
    }


    fun getKehutananList(cari: String) {
        val ikanInstace = RetroInstance.getRetroInstance().create(KehutananService::class.java)
        val call = ikanInstace.getKehutananList(cari)
        call.enqueue(object : Callback<KehutananList>{
            override fun onResponse(call: Call<KehutananList>, response: Response<KehutananList>) {
                if(response.isSuccessful) {
                    kehutananDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<KehutananList>, t: Throwable) {
                kehutananDataList.postValue(null)
            }

        })
    }

}