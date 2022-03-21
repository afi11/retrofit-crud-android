package com.example.pertanian.views.ubikayu

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
import com.example.pertanian.viewmodels.ubikayu.TaniUbiKayuViewModel
import kotlinx.android.synthetic.main.activity_tani_jagung.*

class TaniUbiKayuActivity : AppCompatActivity(), TaniRcyAdapter.OnItemClickListener {

    lateinit var taniRcyAdapter: TaniRcyAdapter
    lateinit var taniUbiKayuViewModel: TaniUbiKayuViewModel
    lateinit var authConfig: AuthConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tani_jagung)

        initRecyclerView()
        authConfig = AuthConfig(this)

        if(authConfig!!.roleUser().equals("user")){
            btnAddPerikanan.visibility = View.GONE
        }

        supportActionBar!!.title = "Produktivitas UbiKayu"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnAddPerikanan.setOnClickListener {
            val intent = Intent(this, TambahEditUbiKayuActivity::class.java)
            intent.putExtra("page", "add")
            startActivity(intent)
        }

    }

    private fun initViewModel() {
        taniUbiKayuViewModel = ViewModelProvider(this).get(TaniUbiKayuViewModel::class.java)
        taniUbiKayuViewModel.getUbiKayuLiveObserveable().observe(this, Observer<PertanianList>{
            if(it == null){
                Toast.makeText(this@TaniUbiKayuActivity, "No result found", Toast.LENGTH_LONG).show()
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
                   taniUbiKayuViewModel.getUbiKayuList(s.toString())
               }else{
                   taniRcyAdapter.taniList.clear()
                   taniRcyAdapter.notifyDataSetChanged()
                   taniUbiKayuViewModel.getUbiKayuList("")
               }
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        taniUbiKayuViewModel.getUbiKayuList("")
    }

    private fun initRecyclerView() {
        rcyPerikanan.apply {
            layoutManager = LinearLayoutManager(this@TaniUbiKayuActivity)
            taniRcyAdapter = TaniRcyAdapter(this@TaniUbiKayuActivity)
            adapter = taniRcyAdapter
        }
    }

    override fun onItemDetailClick(pertanian: Pertanian) {
        val intent = Intent(this, TambahEditUbiKayuActivity::class.java)
        intent.putExtra("id", pertanian.id)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            taniUbiKayuViewModel.getUbiKayuList("")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}