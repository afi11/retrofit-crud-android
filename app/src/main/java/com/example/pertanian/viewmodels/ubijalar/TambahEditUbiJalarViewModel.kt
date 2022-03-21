package com.example.pertanian.viewmodels.ubijalar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditUbiJalarViewModel : ViewModel() {

    lateinit var createNewUbiJalarLiveData: MutableLiveData<PertanianResponse?>
    lateinit var loadUbiJalarData: MutableLiveData<PertanianResponse?>
    lateinit var deleteUbiJalarData: MutableLiveData<PertanianResponse?>

    init {
        createNewUbiJalarLiveData = MutableLiveData()
        loadUbiJalarData = MutableLiveData()
        deleteUbiJalarData = MutableLiveData()
    }

    fun getCreateNewUbiJalarObservable() : MutableLiveData<PertanianResponse?> {
        return createNewUbiJalarLiveData
    }

    fun getLoadUbiJalarObservable() : MutableLiveData<PertanianResponse?> {
        return loadUbiJalarData
    }

    fun deleteUbiJalarObserveable() : MutableLiveData<PertanianResponse?> {
        return deleteUbiJalarData
    }

    fun createUbiJalar(pertanian: Pertanian){
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = retriInstance.createUbiJalar(pertanian)
        call.enqueue(object : Callback<PertanianResponse?> {
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewUbiJalarLiveData.postValue(response.body())
                }else{
                    createNewUbiJalarLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewUbiJalarLiveData.postValue(null)
            }

        })
    }

    fun updateUbiJalar(id: String?, pertanian: Pertanian) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.updateUbiJalar(id!!, pertanian)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewUbiJalarLiveData.postValue(response.body())
                }else{
                    createNewUbiJalarLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewUbiJalarLiveData.postValue(null)
            }

        })
    }

    fun deleteUbiJalar(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.deleteUbiJalar(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    deleteUbiJalarData.postValue(response.body())
                }else{
                    deleteUbiJalarData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                deleteUbiJalarData.postValue(null)
            }

        })
    }

    fun getUbiJalarData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.getUbiJalarBy(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    loadUbiJalarData.postValue(response.body())
                }else{
                    loadUbiJalarData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                loadUbiJalarData.postValue(null)
            }

        })
    }

}