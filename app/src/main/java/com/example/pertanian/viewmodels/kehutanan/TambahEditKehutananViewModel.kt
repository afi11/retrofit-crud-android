package com.example.pertanian.viewmodels.kehutanan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pertanian.instance.RetroInstance
import com.example.pertanian.models.Kehutanan
import com.example.pertanian.models.KehutananResponse
import com.example.pertanian.services.KehutananService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahEditKehutananViewModel : ViewModel() {

    lateinit var createNewKehutananLiveData: MutableLiveData<KehutananResponse?>
    lateinit var loadKehutananData: MutableLiveData<KehutananResponse?>
    lateinit var deleteKehutananData: MutableLiveData<KehutananResponse?>

    init {
        createNewKehutananLiveData = MutableLiveData()
        loadKehutananData = MutableLiveData()
        deleteKehutananData = MutableLiveData()
    }

    fun getCreateNewKehutananObservable() : MutableLiveData<KehutananResponse?> {
        return createNewKehutananLiveData
    }

    fun getLoadKehutananObservable() : MutableLiveData<KehutananResponse?> {
        return loadKehutananData
    }

    fun deleteKehutananObserveable() : MutableLiveData<KehutananResponse?> {
        return deleteKehutananData
    }

    fun createKehutanan(Kehutanan: Kehutanan){
        val retriInstance = RetroInstance.getRetroInstance().create(KehutananService::class.java)
        val call = retriInstance.createKehutanan(Kehutanan)
        call.enqueue(object : Callback<KehutananResponse?> {
            override fun onResponse(
                call: Call<KehutananResponse?>,
                response: Response<KehutananResponse?>
            ) {
                if(response.isSuccessful){
                    createNewKehutananLiveData.postValue(response.body())
                }else{
                    createNewKehutananLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<KehutananResponse?>, t: Throwable) {
                createNewKehutananLiveData.postValue(null)
            }

        })
    }

    fun updateKehutanan(id: String?, Kehutanan: Kehutanan) {
        val retriInstance = RetroInstance.getRetroInstance().create(KehutananService::class.java)
        val call  = retriInstance.updateKehutanan(id!!, Kehutanan)
        call.enqueue(object : Callback<KehutananResponse?>{
            override fun onResponse(
                call: Call<KehutananResponse?>,
                response: Response<KehutananResponse?>
            ) {
                if(response.isSuccessful){
                    createNewKehutananLiveData.postValue(response.body())
                }else{
                    createNewKehutananLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<KehutananResponse?>, t: Throwable) {
                createNewKehutananLiveData.postValue(null)
            }

        })
    }

    fun deleteKehutanan(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(KehutananService::class.java)
        val call  = retriInstance.deleteKehutanan(id!!)
        call.enqueue(object : Callback<KehutananResponse?>{
            override fun onResponse(
                call: Call<KehutananResponse?>,
                response: Response<KehutananResponse?>
            ) {
                if(response.isSuccessful){
                    deleteKehutananData.postValue(response.body())
                }else{
                    deleteKehutananData.postValue(null)
                }
            }

            override fun onFailure(call: Call<KehutananResponse?>, t: Throwable) {
                deleteKehutananData.postValue(null)
            }

        })
    }

    fun getKehutananData(id: String?) {
        val retriInstance = RetroInstance.getRetroInstance().create(KehutananService::class.java)
        val call  = retriInstance.getKehutananBy(id!!)
        call.enqueue(object : Callback<KehutananResponse?>{
            override fun onResponse(
                call: Call<KehutananResponse?>,
                response: Response<KehutananResponse?>
            ) {
                if(response.isSuccessful){
                    loadKehutananData.postValue(response.body())
                }else{
                    loadKehutananData.postValue(null)
                }
            }

            override fun onFailure(call: Call<KehutananResponse?>, t: Throwable) {
                loadKehutananData.postValue(null)
            }

        })
    }

}