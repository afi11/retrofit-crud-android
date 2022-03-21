package com.example.pertanian.views.peternakan

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
import com.example.pertanian.adapters.TernakRcyAdapter
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Peternakan
import com.example.pertanian.models.PeternakanList
import com.example.pertanian.viewmodels.peternakan.TaniPeternakanViewModel
import kotlinx.android.synthetic.main.activity_peternakan.*

class PeternakanActivity : AppCompatActivity(), TernakRcyAdapter.OnItemClickListener {

    lateinit var ternakRcyAdapter: TernakRcyAdapter
    lateinit var peternakanVieModel: TaniPeternakanViewModel
    lateinit var authConfig: AuthConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peternakan)

        authConfig = AuthConfig(this)
        initRecyclerView()

        if(authConfig!!.roleUser().equals("user")){
            btnAddTernak.visibility = View.GONE
        }

        supportActionBar!!.title = "Produktivitas Peternakan"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnAddTernak.setOnClickListener {
            val intent = Intent(this, TambahEditPeternakanActivity::class.java)
            intent.putExtra("page", "add")
            startActivity(intent)
        }

    }

    private fun initViewModel() {
        peternakanVieModel = ViewModelProvider(this).get(TaniPeternakanViewModel::class.java)
        peternakanVieModel.getPeternakanLiveObserveable().observe(this, Observer<PeternakanList>{
            if(it == null){
                Toast.makeText(this@PeternakanActivity, "No result found", Toast.LENGTH_LONG).show()
            }else{
                ternakRcyAdapter.ternakList = it.data.toMutableList()
                ternakRcyAdapter.notifyDataSetChanged()
            }
        })

        editTextTernakSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length > 0){
                    ternakRcyAdapter.ternakList.clear()
                    ternakRcyAdapter.notifyDataSetChanged()
                    peternakanVieModel.getPeternakanList(s.toString())
                }else{
                    ternakRcyAdapter.ternakList.clear()
                    ternakRcyAdapter.notifyDataSetChanged()
                    peternakanVieModel.getPeternakanList("")
                }
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        peternakanVieModel.getPeternakanList("")
    }

    private fun initRecyclerView() {
        rcyTernak.apply {
            layoutManager = LinearLayoutManager(this@PeternakanActivity)
            ternakRcyAdapter = TernakRcyAdapter(this@PeternakanActivity)
            adapter = ternakRcyAdapter
        }
    }

    override fun onItemDetailClick(peternakan: Peternakan) {
        val intent = Intent(this, TambahEditPeternakanActivity::class.java)
        intent.putExtra("id", peternakan.id)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            peternakanVieModel.getPeternakanList("")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}