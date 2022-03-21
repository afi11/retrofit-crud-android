package com.example.pertanian.views.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pertanian.MainActivity
import com.example.pertanian.R
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Auth
import com.example.pertanian.models.AuthResponse
import com.example.pertanian.models.KehutananResponse
import com.example.pertanian.viewmodels.auth.AuthViewModel
import com.example.pertanian.viewmodels.kehutanan.TambahEditKehutananViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var authViewModel: AuthViewModel
    lateinit var authConfig : AuthConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        authConfig = AuthConfig(this)

        intiViewModel()
        createAuthObserveAble()

        buttonLogin.setOnClickListener {
            actLogin()
        }

    }

    private fun actLogin() {
        val auth = Auth("", "",
            editTextEmail.text.toString(),
            editTextPassword.text.toString(),
        "")
        authViewModel.createAuth(auth)
    }

    private fun createAuthObserveAble() {
        authViewModel.getCreateAuthLiveDataObservable().observe(this, Observer<AuthResponse?>{
            if(it == null){
                Toast.makeText(this@LoginActivity, "Gagal Login", Toast.LENGTH_LONG).show()
            }else{
                Log.d("tes",it.data.toString())
                val input : Boolean = authConfig!!.addUserData(it.data!!.id, it.data!!.role)
                if(input){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        })
    }

    private fun intiViewModel() {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }


}