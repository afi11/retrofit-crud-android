package com.example.pertanian.viewmodels.kedelai

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditKedelaiViewModel : ViewModel() {

    lateinit var createNewKedelaiLiveData: MutableLiveData<PertanianResponse?>
    lateinit var loadKedelaiData: MutableLiveData<PertanianResponse?>
    lateinit var deleteKedelaiData: MutableLiveData<PertanianResponse?>

    init {
        createNewKedelaiLiveData = MutableLiveData()
        loadKedelaiData = MutableLiveData()
        deleteKedelaiData = MutableLiveData()
    }

    fun getCreateNewKedelaiObservable() : MutableLiveData<PertanianResponse?> {
        return createNewKedelaiLiveData
    }

    fun getLoadKedelaiObservable() : MutableLiveData<PertanianResponse?> {
        return loadKedelaiData
    }

    fun deleteKedelaiObserveable() : MutableLiveData<PertanianResponse?> {
        return deleteKedelaiData
    }

    fun createKedelai(pertanian: Pertanian){
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = retriInstance.createKedelai(pertanian)
        call.enqueue(object : Callback<PertanianResponse?> {
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewKedelaiLiveData.postValue(response.body())
                }else{
                    createNewKedelaiLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewKedelaiLiveData.postValue(null)
            }

        })
    }

    fun updateKedelai(id: String?, pertanian: Pertanian) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.updateKedelai(id!!, pertanian)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewKedelaiLiveData.postValue(response.body())
                }else{
                    createNewKedelaiLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewKedelaiLiveData.postValue(null)
            }

        })
    }

    fun deleteKedelai(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.deleteKedelai(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    deleteKedelaiData.postValue(response.body())
                }else{
                    deleteKedelaiData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                deleteKedelaiData.postValue(null)
            }

        })
    }

    fun getKedelaiData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.getKedelaiBy(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    loadKedelaiData.postValue(response.body())
                }else{
                    loadKedelaiData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                loadKedelaiData.postValue(null)
            }

        })
    }

}