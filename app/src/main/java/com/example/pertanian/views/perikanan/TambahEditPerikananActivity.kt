package com.example.pertanian.views.perikanan

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
import com.example.pertanian.models.Perikanan
import com.example.pertanian.models.PerikananResponse
import com.example.pertanian.viewmodels.perikanan.TambahEditPerikananViewModel
import kotlinx.android.synthetic.main.activity_tambah_edit.linearLayout8
import kotlinx.android.synthetic.main.activity_tambah_edit.textViewLatitude
import kotlinx.android.synthetic.main.activity_tambah_edit.textViewLongitude
import kotlinx.android.synthetic.main.activity_tambah_edit.textViewTitleButton
import kotlinx.android.synthetic.main.activity_tambah_edit_perikanan.*
import kotlinx.android.synthetic.main.activity_tani_jagung.*

class TambahEditPerikananActivity : AppCompatActivity(), LocationListener {

    lateinit var tambahEditPerikananViewModel: TambahEditPerikananViewModel
    lateinit var locationManager: LocationManager
    lateinit var alertWarning: AlertWarning
    lateinit var authConfig: AuthConfig

    var PerikananId: String? = null
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit_perikanan)

        alertWarning = AlertWarning(this)
        authConfig = AuthConfig(this)
        PerikananId = intent.getStringExtra("id")

        initViewModel()
        createPerikananObservebale()
        getLocation()

        if(PerikananId != null) {
            supportActionBar!!.title = "Edit Produksi Perikanan"
            textViewTitleButton.setText("Update")
            loadPerikananData(PerikananId)
        }else{
            supportActionBar!!.title = "Tambah Produksi Perikanan"
            textViewTitleButton.setText("Tambah")
            linearLayout8.visibility = View.GONE
        }

        if(authConfig!!.roleUser().equals("user")){
            btnAddPerikanan.visibility = View.GONE
            supportActionBar!!.title = "Lihat Produksi Perikanan"
            linearLayout5.visibility = View.GONE
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        buttonSimpanPerikanan.setOnClickListener {
            if(PerikananId != null){
                alertWarning.onAlertDialog(it, "Gunakan Koordinat Lokasi Saat Ini Atau Tidak ?")
                    .setPositiveButton("Ya Gunakan", { dialog, id ->
                        createOrEditPerikanan(PerikananId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
                        dialog.dismiss()
                    })
                    .setNegativeButton("Tidak", { dialog, id ->
                        createOrEditPerikanan(PerikananId, editTextLatPerikanan.text.toString(), editTextLongPerikanan.text.toString())
                        dialog.dismiss()
                    })
                    .show()
            }else{
                createOrEditPerikanan(PerikananId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(PerikananId == null || authConfig!!.roleUser().equals("user")){
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
                alertWarning.onAlertDialog(buttonSimpanPerikanan, "Yakin Hapus Data Ini ?")
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
        tambahEditPerikananViewModel.deletePerikananObserveable().observe(this, Observer<PerikananResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditPerikananActivity,"failed to delete data...",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditPerikananActivity, "Successfully to delete data...",Toast.LENGTH_LONG).show()
                finish()
            }
        })
        tambahEditPerikananViewModel.deletePerikanan(PerikananId)
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    private fun loadPerikananData(id: String?) {
        tambahEditPerikananViewModel.getLoadPerikananObservable().observe(this, Observer<PerikananResponse?>{
            if(it != null){
                editTextPerikananKec.setText(it.data!!.kecamatan)
                editTextPerairanUmum.setText(it.data!!.perairan_umum)
                editTextBudidayaKolam.setText(it.data!!.budidaya_kolam)
                editTextPembenihan.setText(it.data!!.pembenihan)
                editTextLatPerikanan.setText(it.data!!.lokasi_lat)
                editTextLongPerikanan.setText(it.data!!.lokasi_long)
            }
        })
        tambahEditPerikananViewModel.getPerikananData(id)
    }


    private fun createOrEditPerikanan(id: String?, lat: String?, long: String?) {
        val perikanan = Perikanan("", editTextPerikananKec.text.toString(),
            editTextPerairanUmum.text.toString(),
            editTextBudidayaKolam.text.toString(),
            editTextPembenihan.text.toString(),
            lat.toString(),
            long.toString(),"")
        if(id == null){
            tambahEditPerikananViewModel.createPerikanan(perikanan)
        }else{
            tambahEditPerikananViewModel.updatePerikanan(id,perikanan)
        }
    }

    private fun createPerikananObservebale() {
        tambahEditPerikananViewModel.getCreateNewPerikananObservable().observe(this, Observer<PerikananResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditPerikananActivity, "no result found",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditPerikananActivity, "Successfull to add data",Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun initViewModel() {
        tambahEditPerikananViewModel = ViewModelProvider(this).get(TambahEditPerikananViewModel::class.java)
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