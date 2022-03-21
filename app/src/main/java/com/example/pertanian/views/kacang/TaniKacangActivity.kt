package com.example.pertanian.views.kacang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pertanian.R
import com.example.pertanian.adapters.TaniRcyAdapter
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Pertanian
import com.example.pertanian.models.PertanianList
import com.example.pertanian.viewmodels.kacang_tanah.TaniKacangViewModel
import kotlinx.android.synthetic.main.activity_tani_jagung.*

class TaniKacangActivity : AppCompatActivity(), TaniRcyAdapter.OnItemClickListener {

    lateinit var taniRcyAdapter: TaniRcyAdapter
    lateinit var taniKacangViewModel: TaniKacangViewModel
    lateinit var authConfig: AuthConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tani_jagung)

        initRecyclerView()
        authConfig = AuthConfig(this)

        supportActionBar!!.title = "Produktivitas Kacang"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(authConfig!!.roleUser().equals("user")) {
            btnAddPerikanan.visibility = View.GONE
        }

        btnAddPerikanan.setOnClickListener {
            val intent = Intent(this, TambahEditKacangActivity::class.java)
            intent.putExtra("page", "add")
            startActivity(intent)
        }

    }

    private fun initViewModel() {
        taniKacangViewModel = ViewModelProvider(this).get(TaniKacangViewModel::class.java)
        taniKacangViewModel.getkacangLiveObserveable().observe(this, Observer<PertanianList>{
            if(it == null){
                Toast.makeText(this@TaniKacangActivity, "No result found", Toast.LENGTH_LONG).show()
            }else{
                taniRcyAdapter.taniList = it.data.toMutableList()
                taniRcyAdapter.notifyDataSetChanged()
            }
        })

        editTextPerikananSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(s!!.length > 0){
                   taniRcyAdapter.taniList.clear()
                   taniRcyAdapter.notifyDataSetChanged()
                   taniKacangViewModel.getkacangList(s.toString())
               }else{
                   taniRcyAdapter.taniList.clear()
                   taniRcyAdapter.notifyDataSetChanged()
                   taniKacangViewModel.getkacangList("")
               }
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        taniKacangViewModel.getkacangList("")
    }

    private fun initRecyclerView() {
        rcyPerikanan.apply {
            layoutManager = LinearLayoutManager(this@TaniKacangActivity)
            taniRcyAdapter = TaniRcyAdapter(this@TaniKacangActivity)
            adapter = taniRcyAdapter
        }
    }

    override fun onItemDetailClick(pertanian: Pertanian) {
        val intent = Intent(this, TambahEditKacangActivity::class.java)
        intent.putExtra("id", pertanian.id)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            taniKacangViewModel.getkacangList("")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}