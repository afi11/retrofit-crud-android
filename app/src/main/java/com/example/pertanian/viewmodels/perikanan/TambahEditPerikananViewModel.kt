package com.example.pertanian.viewmodels.perikanan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Perikanan
import com.example.pertanian.models.PerikananResponse
import com.example.pertanian.services.PerikananService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditPerikananViewModel : ViewModel() {

    lateinit var createNewPerikananLiveData: MutableLiveData<PerikananResponse?>
    lateinit var loadPerikananData: MutableLiveData<PerikananResponse?>
    lateinit var deletePerikananData: MutableLiveData<PerikananResponse?>

    init {
        createNewPerikananLiveData = MutableLiveData()
        loadPerikananData = MutableLiveData()
        deletePerikananData = MutableLiveData()
    }

    fun getCreateNewPerikananObservable() : MutableLiveData<PerikananResponse?> {
        return createNewPerikananLiveData
    }

    fun getLoadPerikananObservable() : MutableLiveData<PerikananResponse?> {
        return loadPerikananData
    }

    fun deletePerikananObserveable() : MutableLiveData<PerikananResponse?> {
        return deletePerikananData
    }

    fun createPerikanan(perikanan: Perikanan){
        val retriInstance = RetroInstance.getRetroInstance().create(PerikananService::class.java)
        val call = retriInstance.createPerikanan(perikanan)
        call.enqueue(object : Callback<PerikananResponse?> {
            override fun onResponse(
                call: Call<PerikananResponse?>,
                response: Response<PerikananResponse?>
            ) {
                if(response.isSuccessful){
                    createNewPerikananLiveData.postValue(response.body())
                }else{
                    createNewPerikananLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PerikananResponse?>, t: Throwable) {
                createNewPerikananLiveData.postValue(null)
            }

        })
    }

    fun updatePerikanan(id: String?, perikanan: Perikanan) {
        val retriInstance = RetroInstance.getRetroInstance().create(PerikananService::class.java)
        val call  = retriInstance.updatePerikanan(id!!, perikanan)
        call.enqueue(object : Callback<PerikananResponse?>{
            override fun onResponse(
                call: Call<PerikananResponse?>,
                response: Response<PerikananResponse?>
            ) {
                if(response.isSuccessful){
                    createNewPerikananLiveData.postValue(response.body())
                }else{
                    createNewPerikananLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PerikananResponse?>, t: Throwable) {
                createNewPerikananLiveData.postValue(null)
            }

        })
    }

    fun deletePerikanan(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PerikananService::class.java)
        val call  = retriInstance.deletePerikanan(id!!)
        call.enqueue(object : Callback<PerikananResponse?>{
            override fun onResponse(
                call: Call<PerikananResponse?>,
                response: Response<PerikananResponse?>
            ) {
                if(response.isSuccessful){
                    deletePerikananData.postValue(response.body())
                }else{
                    deletePerikananData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PerikananResponse?>, t: Throwable) {
                deletePerikananData.postValue(null)
            }

        })
    }

    fun getPerikananData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PerikananService::class.java)
        val call  = retriInstance.getPerikananBy(id!!)
        call.enqueue(object : Callback<PerikananResponse?>{
            override fun onResponse(
                call: Call<PerikananResponse?>,
                response: Response<PerikananResponse?>
            ) {
                if(response.isSuccessful){
                    loadPerikananData.postValue(response.body())
                }else{
                    loadPerikananData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PerikananResponse?>, t: Throwable) {
                loadPerikananData.postValue(null)
            }

        })
    }

}