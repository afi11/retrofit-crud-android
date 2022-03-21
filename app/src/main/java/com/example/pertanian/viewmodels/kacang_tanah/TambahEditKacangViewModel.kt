package com.example.pertanian.viewmodels.kacang_tanah

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditKacangViewModel : ViewModel() {

    lateinit var createNewKacangLiveData: MutableLiveData<PertanianResponse?>
    lateinit var loadKacangData: MutableLiveData<PertanianResponse?>
    lateinit var deleteKacangData: MutableLiveData<PertanianResponse?>

    init {
        createNewKacangLiveData = MutableLiveData()
        loadKacangData = MutableLiveData()
        deleteKacangData = MutableLiveData()
    }

    fun getCreateNewKacangObservable() : MutableLiveData<PertanianResponse?> {
        return createNewKacangLiveData
    }

    fun getLoadKacangObservable() : MutableLiveData<PertanianResponse?> {
        return loadKacangData
    }

    fun deleteKacangObserveable() : MutableLiveData<PertanianResponse?> {
        return deleteKacangData
    }

    fun createKacang(pertanian: Pertanian){
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = retriInstance.createKacang(pertanian)
        call.enqueue(object : Callback<PertanianResponse?> {
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewKacangLiveData.postValue(response.body())
                }else{
                    createNewKacangLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewKacangLiveData.postValue(null)
            }

        })
    }

    fun updateKacang(id: String?, pertanian: Pertanian) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.updateKacang(id!!, pertanian)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewKacangLiveData.postValue(response.body())
                }else{
                    createNewKacangLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewKacangLiveData.postValue(null)
            }

        })
    }

    fun deleteKacang(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.deleteKacang(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    deleteKacangData.postValue(response.body())
                }else{
                    deleteKacangData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                deleteKacangData.postValue(null)
            }

        })
    }

    fun getKacangData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.getKacangBy(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    loadKacangData.postValue(response.body())
                }else{
                    loadKacangData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                loadKacangData.postValue(null)
            }

        })
    }

}