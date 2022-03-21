package com.example.pertanian.viewmodels.padi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditPadiViewModel : ViewModel() {

    lateinit var createNewPadiLiveData: MutableLiveData<PertanianResponse?>
    lateinit var loadPadiData: MutableLiveData<PertanianResponse?>
    lateinit var deletePadiData: MutableLiveData<PertanianResponse?>

    init {
        createNewPadiLiveData = MutableLiveData()
        loadPadiData = MutableLiveData()
        deletePadiData = MutableLiveData()
    }

    fun getCreateNewPadiObservable() : MutableLiveData<PertanianResponse?> {
        return createNewPadiLiveData
    }

    fun getLoadPadiObservable() : MutableLiveData<PertanianResponse?> {
        return loadPadiData
    }

    fun deletePadiObserveable() : MutableLiveData<PertanianResponse?> {
        return deletePadiData
    }

    fun createPadi(pertanian: Pertanian){
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = retriInstance.createPadi(pertanian)
        call.enqueue(object : Callback<PertanianResponse?> {
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewPadiLiveData.postValue(response.body())
                }else{
                    createNewPadiLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewPadiLiveData.postValue(null)
            }

        })
    }

    fun updatePadi(id: String?, pertanian: Pertanian) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.updatePadi(id!!, pertanian)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewPadiLiveData.postValue(response.body())
                }else{
                    createNewPadiLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewPadiLiveData.postValue(null)
            }

        })
    }

    fun deletePadi(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.deletePadi(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    deletePadiData.postValue(response.body())
                }else{
                    deletePadiData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                deletePadiData.postValue(null)
            }

        })
    }

    fun getPadiData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.getPadiBy(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    loadPadiData.postValue(response.body())
                }else{
                    loadPadiData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                loadPadiData.postValue(null)
            }

        })
    }

}