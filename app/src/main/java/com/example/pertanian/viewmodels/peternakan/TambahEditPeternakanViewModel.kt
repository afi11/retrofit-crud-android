package com.example.pertanian.viewmodels.peternakan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.Peternakan.services.PeternakanService
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.models.Peternakan
import com.example.pertanian.models.PeternakanResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditPeternakanViewModel : ViewModel() {

    lateinit var createNewPeternakanLiveData: MutableLiveData<PeternakanResponse?>
    lateinit var loadPeternakanData: MutableLiveData<PeternakanResponse?>
    lateinit var deletePeternakanData: MutableLiveData<PeternakanResponse?>

    init {
        createNewPeternakanLiveData = MutableLiveData()
        loadPeternakanData = MutableLiveData()
        deletePeternakanData = MutableLiveData()
    }

    fun getCreateNewPeternakanObservable() : MutableLiveData<PeternakanResponse?> {
        return createNewPeternakanLiveData
    }

    fun getLoadPeternakanObservable() : MutableLiveData<PeternakanResponse?> {
        return loadPeternakanData
    }

    fun deletePeternakanObserveable() : MutableLiveData<PeternakanResponse?> {
        return deletePeternakanData
    }

    fun createPeternakan(peternakan: Peternakan){
        val retriInstance = RetroInstance.getRetroInstance().create(PeternakanService::class.java)
        val call = retriInstance.createTernak(peternakan)
        call.enqueue(object : Callback<PeternakanResponse?> {
            override fun onResponse(
                call: Call<PeternakanResponse?>,
                response: Response<PeternakanResponse?>
            ) {
                if(response.isSuccessful){
                    createNewPeternakanLiveData.postValue(response.body())
                }else{
                    createNewPeternakanLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PeternakanResponse?>, t: Throwable) {
                createNewPeternakanLiveData.postValue(null)
            }

        })
    }

    fun updatePeternakan(id: String?, peternakan: Peternakan) {
        val retriInstance = RetroInstance.getRetroInstance().create(PeternakanService::class.java)
        val call  = retriInstance.updateTernak(id!!, peternakan)
        call.enqueue(object : Callback<PeternakanResponse?>{
            override fun onResponse(
                call: Call<PeternakanResponse?>,
                response: Response<PeternakanResponse?>
            ) {
                if(response.isSuccessful){
                    createNewPeternakanLiveData.postValue(response.body())
                }else{
                    createNewPeternakanLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PeternakanResponse?>, t: Throwable) {
                createNewPeternakanLiveData.postValue(null)
            }

        })
    }

    fun deletePeternakan(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PeternakanService::class.java)
        val call  = retriInstance.deleteTernak(id!!)
        call.enqueue(object : Callback<PeternakanResponse?>{
            override fun onResponse(
                call: Call<PeternakanResponse?>,
                response: Response<PeternakanResponse?>
            ) {
                if(response.isSuccessful){
                    deletePeternakanData.postValue(response.body())
                }else{
                    deletePeternakanData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PeternakanResponse?>, t: Throwable) {
                deletePeternakanData.postValue(null)
            }

        })
    }

    fun getPeternakanData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PeternakanService::class.java)
        val call  = retriInstance.getTernakBy(id!!)
        call.enqueue(object : Callback<PeternakanResponse?>{
            override fun onResponse(
                call: Call<PeternakanResponse?>,
                response: Response<PeternakanResponse?>
            ) {
                if(response.isSuccessful){
                    loadPeternakanData.postValue(response.body())
                }else{
                    loadPeternakanData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PeternakanResponse?>, t: Throwable) {
                loadPeternakanData.postValue(null)
            }

        })
    }

}