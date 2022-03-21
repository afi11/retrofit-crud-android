package com.example.pertanian.views.kehutanan

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
import com.example.pertanian.models.Kehutanan
import com.example.pertanian.models.KehutananResponse
import com.example.pertanian.viewmodels.kehutanan.TambahEditKehutananViewModel
import kotlinx.android.synthetic.main.activity_kehutanan.*
import kotlinx.android.synthetic.main.activity_tambah_edit.linearLayout8
import kotlinx.android.synthetic.main.activity_tambah_edit.textViewLatitude
import kotlinx.android.synthetic.main.activity_tambah_edit.textViewLongitude
import kotlinx.android.synthetic.main.activity_tambah_edit.textViewTitleButton
import kotlinx.android.synthetic.main.activity_tambah_edit_kehutanan.*

class TambahEditKehutananActivity : AppCompatActivity(), LocationListener {

    lateinit var tambahEditKehutananViewModel: TambahEditKehutananViewModel
    lateinit var locationManager: LocationManager
    lateinit var alertWarning: AlertWarning
    lateinit var authConfig: AuthConfig
    var KehutananId: String? = null
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_edit_kehutanan)

        authConfig = AuthConfig(this)
        alertWarning = AlertWarning(this)
        KehutananId = intent.getStringExtra("id")

        initViewModel()
        createKehutananObservebale()
        getLocation()

        if(KehutananId != null) {
            supportActionBar!!.title = "Edit Kehutanan"
            textViewTitleButton.setText("Update")
            loadKehutananData(KehutananId)
        }else{
            supportActionBar!!.title = "Tambah Kehutanan"
            textViewTitleButton.setText("Tambah")
            linearLayout8.visibility = View.GONE
        }

        if(authConfig!!.roleUser().equals("user")){
            buttonSimpanKehutanan.visibility = View.GONE
            linearLayout5.visibility = View.GONE
            supportActionBar!!.title = "Lihat Kehutanan"
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        buttonSimpanKehutanan.setOnClickListener {
            if(KehutananId != null){
                alertWarning.onAlertDialog(it, "Gunakan Koordinat Lokasi Saat Ini Atau Tidak ?")
                    .setPositiveButton("Ya Gunakan", { dialog, id ->
                        createOrEditKehutanan(KehutananId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
                        dialog.dismiss()
                    })
                    .setNegativeButton("Tidak", { dialog, id ->
                        createOrEditKehutanan(KehutananId, editTextLatKehutanan.text.toString(), editTextLongKehutanan.text.toString())
                        dialog.dismiss()
                    })
                    .show()
            }else{
                createOrEditKehutanan(KehutananId, textViewLatitude.text.toString(), textViewLongitude.text.toString())
            }
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(KehutananId == null || authConfig!!.roleUser().equals("user")){
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
                alertWarning.onAlertDialog(buttonSimpanKehutanan, "Yakin Hapus Data Ini ?")
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
        tambahEditKehutananViewModel.deleteKehutananObserveable().observe(this, Observer<KehutananResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditKehutananActivity,"failed to delete data...",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditKehutananActivity, "Successfully to delete data...",Toast.LENGTH_LONG).show()
                finish()
            }
        })
        tambahEditKehutananViewModel.deleteKehutanan(KehutananId)
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    private fun loadKehutananData(id: String?) {
        tambahEditKehutananViewModel.getLoadKehutananObservable().observe(this, Observer<KehutananResponse?>{
            if(it != null){
                editTextTanamanHutan.setText(it.data!!.tanaman_hutan)
                editTextProduksi.setText(it.data!!.produksi)
                editTextNilaiProduksi.setText(it.data!!.nilai_produksi)
                editTextLatKehutanan.setText(it.data!!.lokasi_lat)
                editTextLongKehutanan.setText(it.data!!.lokasi_long)
            }
        })
        tambahEditKehutananViewModel.getKehutananData(id)
    }


    private fun createOrEditKehutanan(id: String?, lat: String?, long: String?) {
        val Kehutanan = Kehutanan("",
            editTextTanamanHutan.text.toString(),
            editTextProduksi.text.toString(),
            editTextNilaiProduksi.text.toString(),
            lat.toString(),
            long.toString(),"")
        if(id == null){
            tambahEditKehutananViewModel.createKehutanan(Kehutanan)
        }else{
            tambahEditKehutananViewModel.updateKehutanan(id,Kehutanan)
        }
    }

    private fun createKehutananObservebale() {
        tambahEditKehutananViewModel.getCreateNewKehutananObservable().observe(this, Observer<KehutananResponse?>{
            if(it == null){
                Toast.makeText(this@TambahEditKehutananActivity, "no result found",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@TambahEditKehutananActivity, "Successfull to add data",Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun initViewModel() {
        tambahEditKehutananViewModel = ViewModelProvider(this).get(TambahEditKehutananViewModel::class.java)
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