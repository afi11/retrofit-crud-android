package com.example.pertanian.views.ubijalar

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pertanian.BuildConfig
import com.example.pertanian.R
import com.example.pertanian.components.AlertWarning
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.viewmodels.ubijalar.TambahEditUbiJalarViewModel
import kotlinx.android.synthetic.main.activity_tambah_edit.*

class TambahEditUbiJalarActivity : AppCompatActivity(), LocationListener {

    lateinit var tambahEditPadaiViewModel: TambahEditUbiJalarViewModel
    lateinit var locationManager: LocationManager
    lateinit var alertWarning: AlertWarning
    lateinit var authConfig: AuthConfig

    var UbiJalarId: String? = null
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit)

        authConfig = AuthConfig(this)
        alertWarning = AlertWarning(this)
        UbiJalarId = intent.getStringExtra("id")

        initViewModel()
        createUbiJalarObservebale()
        getLocation()

        if(UbiJalarId != null) {
            supportActionBar!!.title = "Edit Produksi Ubi Jalar"
            textViewTitleButton.setText("Update")
            loadUbiJalarData(UbiJalarId)
        }else{
            supportActionBar!!.title = "Tambah Produksi Ubi Jalar"
            textViewTitleButton.setText("Tambah")
            linearLayout8.visibility = View.GONE
        }

        if(authConfig!!.roleUser().equals("user")){
            linearLayout5.visibility = View.GONE
            supportActionBar!!.title = "Lihat Produksi Ubi Jalar"
            buttonSimpanJagung.visibility = View.GONE
        }


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        buttonSimpanJagung.setOnClickListener {
            if(UbiJalarId != null){
                alertWarning.onAlertDialog(it, "Gunakan Koordinat Lokasi Saat Ini Atau Tidak ?")
                    .setPositiveButton("Ya Gunakan", { dialog, id ->
                        createOrEditUbiJalar(UbiJalarId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
                        dialog.dismiss()
                    })
                    .setNegativeButton("Tidak", { dialog, id ->
                        createOrEditUbiJalar(UbiJalarId, editTextLatJagung.text.toString(), editTextLongJagung.text.toString())
                        dialog.dismiss()
                    })
                    .show()
            }else{
                createOrEditUbiJalar(UbiJalarId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(UbiJalarId == null || authConfig!!.roleUser().equals("user")){
            val item : MenuItem = menu!!.findItem(R.id.delete)
            item.setVisible(false)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> {
                alertWarning.onAlertDialog(buttonSimpanJagung, "Yakin Hapus Data Ini ?")
                    .setPositiveButton("Ya", { dialog, id ->
                        deleteData()
                        dialog.dismiss()
                    })
                    .setNegativeButton("Tidak", { dialog, id ->
                        dialog.dismiss()
                    })
                    .show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteData() {
        tambahEditPadaiViewModel.deleteUbiJalarObserveable().observe(this, Observer<PertanianResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditUbiJalarActivity,"failed to delete data...",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditUbiJalarActivity, "Successfully to delete data...",Toast.LENGTH_LONG).show()
                finish()
            }
        })
        tambahEditPadaiViewModel.deleteUbiJalar(UbiJalarId)
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    private fun loadUbiJalarData(id: String?) {
        tambahEditPadaiViewModel.getLoadUbiJalarObservable().observe(this, Observer<PertanianResponse?>{
            if(it != null){
                editTextJagungKec.setText(it.data!!.kecamatan)
                editTextJagungLuasPanen.setText(it.data!!.luas_panen)
                editTextJagungProduksi.setText(it.data!!.produksi)
                editTextRata2Panen.setText(it.data!!.rata_rata)
                editTextLatJagung.setText(it.data!!.lokasi_lat)
                editTextLongJagung.setText(it.data!!.lokasi_long)
            }
        })
        tambahEditPadaiViewModel.getUbiJalarData(id)
    }


    private fun createOrEditUbiJalar(id: String?, lat: String?, long: String?) {
        val jagung = Pertanian("", editTextJagungKec.text.toString(),
            editTextJagungProduksi.text.toString(),
            editTextJagungLuasPanen.text.toString(),
            editTextRata2Panen.text.toString(),
            lat.toString(),
            long.toString(),"")
        if(id == null){
            tambahEditPadaiViewModel.createUbiJalar(jagung)
        }else{
            tambahEditPadaiViewModel.updateUbiJalar(id,jagung)
        }
    }

    private fun createUbiJalarObservebale() {
        tambahEditPadaiViewModel.getCreateNewUbiJalarObservable().observe(this, Observer<PertanianResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditUbiJalarActivity, "no result found",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditUbiJalarActivity, "Successfull to add data",Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun initViewModel() {
        tambahEditPadaiViewModel = ViewModelProvider(this).get(TambahEditUbiJalarViewModel::class.java)
    }

    override fun onLocationChanged(location: Location) {
        textViewLatitude.setText(location.latitude.toString())
        textViewLongitude.setText(location.longitude.toString())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}