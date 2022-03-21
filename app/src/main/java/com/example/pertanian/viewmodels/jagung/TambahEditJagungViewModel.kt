package com.example.pertanian.viewmodels.jagung

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.services.PertanianService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditJagungViewModel : ViewModel() {

    lateinit var createNewJagungLiveData: MutableLiveData<PertanianResponse?>
    lateinit var loadJagungData: MutableLiveData<PertanianResponse?>
    lateinit var deleteJagungData: MutableLiveData<PertanianResponse?>

    init {
        createNewJagungLiveData = MutableLiveData()
        loadJagungData = MutableLiveData()
        deleteJagungData = MutableLiveData()
    }

    fun getCreateNewJagungObservable() : MutableLiveData<PertanianResponse?> {
        return createNewJagungLiveData
    }

    fun getLoadJagungObservable() : MutableLiveData<PertanianResponse?> {
        return loadJagungData
    }

    fun deleteJagungObserveable() : MutableLiveData<PertanianResponse?> {
        return deleteJagungData
    }

    fun createJagung(pertanian: Pertanian){
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call = retriInstance.createJagung(pertanian)
        call.enqueue(object : Callback<PertanianResponse?> {
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewJagungLiveData.postValue(response.body())
                }else{
                    createNewJagungLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewJagungLiveData.postValue(null)
            }

        })
    }

    fun updateJagung(id: String?, pertanian: Pertanian) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.updateJagung(id!!, pertanian)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    createNewJagungLiveData.postValue(response.body())
                }else{
                    createNewJagungLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                createNewJagungLiveData.postValue(null)
            }

        })
    }

    fun deleteJagung(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.deleteJagung(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    deleteJagungData.postValue(response.body())
                }else{
                    deleteJagungData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                deleteJagungData.postValue(null)
            }

        })
    }

    fun getJagungData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(PertanianService::class.java)
        val call  = retriInstance.getJagungBy(id!!)
        call.enqueue(object : Callback<PertanianResponse?>{
            override fun onResponse(
                call: Call<PertanianResponse?>,
                response: Response<PertanianResponse?>
            ) {
                if(response.isSuccessful){
                    loadJagungData.postValue(response.body())
                }else{
                    loadJagungData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PertanianResponse?>, t: Throwable) {
                loadJagungData.postValue(null)
            }

        })
    }

}