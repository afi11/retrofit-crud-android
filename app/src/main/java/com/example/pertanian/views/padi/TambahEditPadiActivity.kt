package com.example.pertanian.views.padi

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
import com.example.pertanian.R
import com.example.pertanian.components.AlertWarning
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianResponse
import com.example.pertanian.viewmodels.padi.TambahEditPadiViewModel
import kotlinx.android.synthetic.main.activity_tambah_edit.*

class TambahEditPadiActivity : AppCompatActivity(), LocationListener {

    lateinit var tambahEditPadaiViewModel: TambahEditPadiViewModel
    lateinit var locationManager: LocationManager
    lateinit var alertWarning: AlertWarning
    lateinit var authConfig: AuthConfig

    var PadiId: String? = null
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit)

        alertWarning = AlertWarning(this)
        authConfig = AuthConfig(this)
        PadiId = intent.getStringExtra("id")

        initViewModel()
        createPadiObservebale()
        getLocation()

        if(PadiId != null) {
            supportActionBar!!.title = "Edit Produksi Padi"
            textViewTitleButton.setText("Update")
            loadPadiData(PadiId)
        }else{
            supportActionBar!!.title = "Tambah Produksi Padi"
            textViewTitleButton.setText("Tambah")
            linearLayout8.visibility = View.GONE
        }

        if(authConfig!!.roleUser().equals("user")){
            buttonSimpanJagung.visibility = View.GONE
            linearLayout5.visibility = View.GONE
            supportActionBar!!.title = "lihat Produksi Padi"
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        buttonSimpanJagung.setOnClickListener {
            if(PadiId != null){
                alertWarning.onAlertDialog(it, "Gunakan Koordinat Lokasi Saat Ini Atau Tidak ?")
                    .setPositiveButton("Ya Gunakan", { dialog, id ->
                        createOrEditPadi(PadiId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
                        dialog.dismiss()
                    })
                    .setNegativeButton("Tidak", { dialog, id ->
                        createOrEditPadi(PadiId, editTextLatJagung.text.toString(), editTextLongJagung.text.toString())
                        dialog.dismiss()
                    })
                    .show()
            }else{
                createOrEditPadi(PadiId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(PadiId == null || authConfig!!.roleUser().equals("user")){
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
        tambahEditPadaiViewModel.deletePadiObserveable().observe(this, Observer<PertanianResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditPadiActivity,"failed to delete data...",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditPadiActivity, "Successfully to delete data...",Toast.LENGTH_LONG).show()
                finish()
            }
        })
        tambahEditPadaiViewModel.deletePadi(PadiId)
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    private fun loadPadiData(id: String?) {
        tambahEditPadaiViewModel.getLoadPadiObservable().observe(this, Observer<PertanianResponse?>{
            if(it != null){
                editTextJagungKec.setText(it.data!!.kecamatan)
                editTextJagungLuasPanen.setText(it.data!!.luas_panen)
                editTextJagungProduksi.setText(it.data!!.produksi)
                editTextRata2Panen.setText(it.data!!.rata_rata)
                editTextLatJagung.setText(it.data!!.lokasi_lat)
                editTextLongJagung.setText(it.data!!.lokasi_long)
            }
        })
        tambahEditPadaiViewModel.getPadiData(id)
    }


    private fun createOrEditPadi(id: String?, lat: String?, long: String?) {
        val jagung = Pertanian("", editTextJagungKec.text.toString(),
            editTextJagungProduksi.text.toString(),
            editTextJagungLuasPanen.text.toString(),
            editTextRata2Panen.text.toString(),
            lat.toString(),
            long.toString(),"")
        if(id == null){
            tambahEditPadaiViewModel.createPadi(jagung)
        }else{
            tambahEditPadaiViewModel.updatePadi(id,jagung)
        }
    }

    private fun createPadiObservebale() {
        tambahEditPadaiViewModel.getCreateNewPadiObservable().observe(this, Observer<PertanianResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditPadiActivity, "no result found",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditPadiActivity, "Successfull to add data",Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun initViewModel() {
        tambahEditPadaiViewModel = ViewModelProvider(this).get(TambahEditPadiViewModel::class.java)
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