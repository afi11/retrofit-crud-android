package com.example.pertanian.views.peternakan

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
import com.example.pertanian.models.Peternakan
import com.example.pertanian.models.PeternakanResponse
import com.example.pertanian.viewmodels.peternakan.TambahEditPeternakanViewModel
import kotlinx.android.synthetic.main.activity_tambah_edit.*
import kotlinx.android.synthetic.main.activity_tambah_edit_peternakan.*
import kotlinx.android.synthetic.main.activity_tambah_edit_peternakan.linearLayout8
import kotlinx.android.synthetic.main.activity_tambah_edit_peternakan.textViewLatitude
import kotlinx.android.synthetic.main.activity_tambah_edit_peternakan.textViewLongitude
import kotlinx.android.synthetic.main.activity_tambah_edit_peternakan.textViewTitleButton

class TambahEditPeternakanActivity : AppCompatActivity(), LocationListener {

    lateinit var tambahEditPeternakanViewModel: TambahEditPeternakanViewModel
    private val locationPermissionCode = 2
    lateinit var locationManager: LocationManager
    lateinit var alertWarning: AlertWarning
    lateinit var authConfig: AuthConfig

    var peternakanId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit_peternakan)

        alertWarning = AlertWarning(this)
        authConfig = AuthConfig(this)
        peternakanId = intent.getStringExtra("id")

        initViewModel()
        createPeternakanObservebale()
        getLocation()

        if(peternakanId != null) {
            supportActionBar!!.title = "Edit Peternakan"
            textViewTitleButton.setText("Update")
            loadTernakData(peternakanId)
        }else{
            supportActionBar!!.title = "Tambah Peternakan"
            textViewTitleButton.setText("Tambah")
            linearLayout8.visibility = View.GONE
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(authConfig!!.roleUser().equals("user")){
            buttonSimpanTernak.visibility = View.GONE
            supportActionBar!!.title = "Lihat Peternakan"
            linearLayout6.visibility = View.GONE
        }

        buttonSimpanTernak.setOnClickListener {
            if(peternakanId != null){
                alertWarning.onAlertDialog(it, "Gunakan Koordinat Lokasi Saat Ini Atau Tidak ?")
                    .setPositiveButton("Ya Gunakan", { dialog, id ->
                        createOrEditPeternakan(peternakanId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
                        dialog.dismiss()
                    })
                    .setNegativeButton("Tidak", { dialog, id ->
                        createOrEditPeternakan(peternakanId, editTextLatJagung.text.toString(), editTextLongJagung.text.toString())
                        dialog.dismiss()
                    })
                    .show()
            }else{
                createOrEditPeternakan(peternakanId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
            }
        }

    }

    private fun createOrEditPeternakan(id: String?, lat: String?, long: String?) {
        val ternak = Peternakan("", editTextTernakKec.text.toString(),
            editTextSapiPotongProduksi.text.toString(),
            editTextKambing.text.toString(),
            editTextAyamKampung.text.toString(),
            lat.toString(),
            long.toString(),"")
        if(id == null){
            tambahEditPeternakanViewModel.createPeternakan(ternak)
        }else{
            tambahEditPeternakanViewModel.updatePeternakan(id,ternak)
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    private fun createPeternakanObservebale() {
        tambahEditPeternakanViewModel.getCreateNewPeternakanObservable().observe(this, Observer<PeternakanResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditPeternakanActivity, "no result found",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditPeternakanActivity, "Successfull to add data",Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun initViewModel() {
        tambahEditPeternakanViewModel = ViewModelProvider(this).get(TambahEditPeternakanViewModel::class.java)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(peternakanId == null || authConfig!!.roleUser().equals("user")){
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
                alertWarning.onAlertDialog(buttonSimpanTernak, "Yakin Hapus Data Ini ?")
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
        tambahEditPeternakanViewModel.deletePeternakanObserveable().observe(this, Observer<PeternakanResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditPeternakanActivity,"failed to delete data...",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditPeternakanActivity, "Successfully to delete data...",Toast.LENGTH_LONG).show()
                finish()
            }
        })
        tambahEditPeternakanViewModel.deletePeternakan(peternakanId)
    }

    private fun loadTernakData(id: String?) {
        tambahEditPeternakanViewModel.getLoadPeternakanObservable().observe(this, Observer<PeternakanResponse?>{
            if(it != null){
                editTextTernakKec.setText(it.data!!.kecamatan)
                editTextSapiPotongProduksi.setText(it.data!!.sapi_potong)
                editTextKambing.setText(it.data!!.kambing)
                editTextAyamKampung.setText(it.data!!.ayam_kampung)
                editTextLatTernak.setText(it.data!!.lokasi_lat)
                editTextLongTernak.setText(it.data!!.lokasi_long)
            }
        })
        tambahEditPeternakanViewModel.getPeternakanData(id)
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