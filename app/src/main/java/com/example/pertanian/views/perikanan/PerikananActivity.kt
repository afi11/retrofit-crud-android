package com.example.pertanian.views.perikanan

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
import com.example.pertanian.adapters.TernakPerikananAdapter
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Perikanan
import com.example.pertanian.models.PerikananList
import com.example.pertanian.viewmodels.perikanan.TaniPerikananViewModel
import kotlinx.android.synthetic.main.activity_perikanan.*

class PerikananActivity : AppCompatActivity(), TernakPerikananAdapter.OnItemClickListener {

    lateinit var ternakPerikananAdapter: TernakPerikananAdapter
    lateinit var taniPerikananViewModel: TaniPerikananViewModel
    lateinit var authConfig: AuthConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tani_jagung)

        initRecyclerView()
        authConfig = AuthConfig(this)

        if(authConfig!!.roleUser().equals("user")){
            btnAddPerikanan.visibility = View.GONE
        }

        supportActionBar!!.title = "Perikanan"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnAddPerikanan.setOnClickListener {
            val intent = Intent(this, TambahEditPerikananActivity::class.java)
            intent.putExtra("page", "add")
            startActivity(intent)
        }

    }

    private fun initViewModel() {
        taniPerikananViewModel = ViewModelProvider(this).get(TaniPerikananViewModel::class.java)
        taniPerikananViewModel.getPerikananLiveObserveable().observe(this, Observer<PerikananList>{
            if(it == null){
                Toast.makeText(this@PerikananActivity, "No result found", Toast.LENGTH_LONG).show()
            }else{
                ternakPerikananAdapter.perikananList = it.data.toMutableList()
                ternakPerikananAdapter.notifyDataSetChanged()
            }
        })

        editTextPerikananSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(s!!.length > 0){
                   ternakPerikananAdapter.perikananList.clear()
                   ternakPerikananAdapter.notifyDataSetChanged()
                   taniPerikananViewModel.getperikananList(s.toString())
               }else{
                   ternakPerikananAdapter.perikananList.clear()
                   ternakPerikananAdapter.notifyDataSetChanged()
                   taniPerikananViewModel.getperikananList("")
               }
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        taniPerikananViewModel.getperikananList("")
    }

    private fun initRecyclerView() {
        rcyPerikanan.apply {
            layoutManager = LinearLayoutManager(this@PerikananActivity)
            ternakPerikananAdapter = TernakPerikananAdapter(this@PerikananActivity)
            adapter = ternakPerikananAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            taniPerikananViewModel.getperikananList("")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onItemDetailClick(perikanan: Perikanan) {
        val intent = Intent(this, TambahEditPerikananActivity::class.java)
        intent.putExtra("id", perikanan.id)
        startActivity(intent)
    }
}

