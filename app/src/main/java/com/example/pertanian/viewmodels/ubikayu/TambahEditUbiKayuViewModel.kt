package com.example.pertanian.viewmodels.ubikayu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditUbiKayuViewModel : ViewModel() {

    lateinit var createNewUbiKayuLiveData: MutableLiveData<PertanianResponse?>
    lateinit var loadUbiKayuData: MutableLiveData<PertanianResponse?>
    lateinit var deleteUbiKayuData: MutableLiveData<PertanianResponse?>

    init {
        createNewUbiKayuLiveData = MutableLiveData()
        loadUbiKayuData = MutableLiveData()
        deleteUbiKayuData = MutableLiveData()
    }

    fun getCreateNewUbiKayuObservable() : MutableLiveData<PertanianResponse?> {
        return createNewUbiKayuLiveData
    }

    fun getLoadUbiKayuObservable() : MutableLiveData<PertanianResponse?> {
        return loadUbiKayuData
    }

    fun deleteUbiKayuObserveable() : MutableLiveData<PertanianResponse?> {
        return deleteUbiKayuData
    }

    fun createUbiKayu(pertanian: Pertanian){
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = retriInstance.createUbiKayu(pertanian)
        call.enqueue(object : Callback<PertanianResponse?> {
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewUbiKayuLiveData.postValue(response.body())
                }else{
                    createNewUbiKayuLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewUbiKayuLiveData.postValue(null)
            }

        })
    }

    fun updateUbiKayu(id: String?, pertanian: Pertanian) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.updateUbiKayu(id!!, pertanian)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewUbiKayuLiveData.postValue(response.body())
                }else{
                    createNewUbiKayuLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewUbiKayuLiveData.postValue(null)
            }

        })
    }

    fun deleteUbiKayu(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.deleteUbiKayu(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    deleteUbiKayuData.postValue(response.body())
                }else{
                    deleteUbiKayuData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                deleteUbiKayuData.postValue(null)
            }

        })
    }

    fun getUbiKayuData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.getUbiKayuBy(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    loadUbiKayuData.postValue(response.body())
                }else{
                    loadUbiKayuData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                loadUbiKayuData.postValue(null)
            }

        })
    }

}