package com.example.pertanian

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pertanian.components.AlertWarning
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.AuthResponse
import com.example.pertanian.viewmodels.auth.AuthViewModel
import com.example.pertanian.views.auth.LoginActivity
import com.example.pertanian.views.jagung.TaniJagungActivity
import com.example.pertanian.views.kacang.TaniKacangActivity
import com.example.pertanian.views.kedelai.TaniKedelaiActivity
import com.example.pertanian.views.kehutanan.KehutananActivity
import com.example.pertanian.views.padi.TaniPadiActivity
import com.example.pertanian.views.perikanan.PerikananActivity
import com.example.pertanian.views.peternakan.PeternakanActivity
import com.example.pertanian.views.ubijalar.TaniUbiJalarActivity
import com.example.pertanian.views.ubikayu.TaniUbiKayuActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var authViewModel: AuthViewModel
    lateinit var authConfig: AuthConfig
    lateinit var alertWarning: AlertWarning

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authConfig = AuthConfig(this)
        alertWarning = AlertWarning(this)
        intiViewModel()
        loadAuthObserveAble()
        if(!authConfig!!.cekUserIsLoggin()){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        supportActionBar!!.hide()

        dashJagung.setOnClickListener {
            val intent = Intent(this, TaniJagungActivity::class.java)
            startActivity(intent)
        }

        dashKacangTanah.setOnClickListener {
            val intent = Intent(this, TaniKacangActivity::class.java)
            startActivity(intent)
        }

        dashKedelai.setOnClickListener {
            val intent = Intent(this, TaniKedelaiActivity::class.java)
            startActivity(intent)
        }

        dashPadai.setOnClickListener {
            val intent = Intent(this, TaniPadiActivity::class.java)
            startActivity(intent)
        }

        dashUbiJalar.setOnClickListener {
            val intent = Intent(this, TaniUbiJalarActivity::class.java)
            startActivity(intent)
        }

        dashUbiKayu.setOnClickListener {
            val intent = Intent(this, TaniUbiKayuActivity::class.java)
            startActivity(intent)
        }

        dashUbiPeternakan.setOnClickListener {
            val intent = Intent(this, PeternakanActivity::class.java)
            startActivity(intent)
        }

        dashPerikanan.setOnClickListener {
            val intent = Intent(this, PerikananActivity::class.java)
            startActivity(intent)
        }

        dashKehutanan.setOnClickListener {
            val intent = Intent(this, KehutananActivity::class.java)
            startActivity(intent)
        }

        rowToLogout.setOnClickListener {
            alertWarning.onAlertDialog(it, "Apakah Anda Ingin Logout Akun ?")
                .setPositiveButton("Ya", { dialog, id ->
                    if(authConfig.logoutUser()){
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                })
                .setNegativeButton("Tidak", { dialog, id ->
                    dialog.dismiss()
                })
                .show()
        }

    }

    private fun loadAuthObserveAble() {
        authViewModel.getLoadAuthDataObservable().observe(this, Observer<AuthResponse?>{
            if(it == null){
                Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show()
            }else{
                textViewUserLoadInfo.setText("Selamat Datang "+it.data!!.name)
                textViewLetterUser.setText(it.data!!.name.take(1))
            }
        })
        authViewModel.getAuthData(authConfig!!.userId())
    }

    private fun intiViewModel() {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }
}