package com.example.pertanian.views.kehutanan

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pertanian.R
import com.example.pertanian.adapters.KehutananRcyAdapter
import com.example.pertanian.config.AuthConfig
import com.example.pertanian.models.Kehutanan
import com.example.pertanian.models.KehutananList
import com.example.pertanian.viewmodels.kehutanan.TaniKehutananViewModel
import kotlinx.android.synthetic.main.activity_kehutanan.*

class KehutananActivity : AppCompatActivity(), KehutananRcyAdapter.OnItemClickListener {

    lateinit var kehutananRcyAdapter: KehutananRcyAdapter
    lateinit var taniKehutananViewModel: TaniKehutananViewModel
    lateinit var authConfig: AuthConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kehutanan)

        authConfig = AuthConfig(this)
        initRecyclerView()

        supportActionBar!!.title = "Kehutanan"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if(authConfig!!.roleUser().equals("user")){
            btnAddKehutanan.visibility = View.GONE
        }

        btnAddKehutanan.setOnClickListener {
            val intent = Intent(this, TambahEditKehutananActivity::class.java)
            intent.putExtra("page", "add")
            startActivity(intent)
        }

    }

    private fun initViewModel() {
        taniKehutananViewModel = ViewModelProvider(this).get(TaniKehutananViewModel::class.java)
        taniKehutananViewModel.getKehutananLiveObserveable().observe(this, Observer<KehutananList>{
            if(it == null){
                Toast.makeText(this@KehutananActivity, "No result found", Toast.LENGTH_LONG).show()
            }else{
                kehutananRcyAdapter.hutanList = it.data.toMutableList()
                kehutananRcyAdapter.notifyDataSetChanged()
            }
        })

        editTextKehutananSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(s!!.length > 0){
                   kehutananRcyAdapter.hutanList.clear()
                   kehutananRcyAdapter.notifyDataSetChanged()
                   taniKehutananViewModel.getKehutananList(s.toString())
               }else{
                   kehutananRcyAdapter.hutanList.clear()
                   kehutananRcyAdapter.notifyDataSetChanged()
                   taniKehutananViewModel.getKehutananList("")
               }
            }

            override fun afterTextChanged(s: Editable?) { }

        })
        taniKehutananViewModel.getKehutananList("")
    }

    private fun initRecyclerView() {
        rcyKehutanan.apply {
            layoutManager = LinearLayoutManager(this@KehutananActivity)
            kehutananRcyAdapter = KehutananRcyAdapter(this@KehutananActivity)
            adapter = kehutananRcyAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000){
            taniKehutananViewModel.getKehutananList("")
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onItemDetailClick(kehutanan: Kehutanan) {
        val intent = Intent(this, TambahEditKehutananActivity::class.java)
        intent.putExtra("id", kehutanan.id)
        startActivity(intent)
    }
}

