package com.example.pertanian.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertanian.R
import com.example.pertanian.models.Peternakan
import com.example.pertanian.utils.Tanggal
import kotlinx.android.synthetic.main.row_peternakan.view.*

class TernakRcyAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<TernakRcyAdapter.ViewHolder>()  {

    var ternakList = mutableListOf<Peternakan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TernakRcyAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_peternakan, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TernakRcyAdapter.ViewHolder, position: Int) {
        holder.bind(ternakList[position])
        if(position > 0){
            var tahun = Tanggal.getOnlyYear(ternakList[position].created_at)
            var tahunPrev = Tanggal.getOnlyYear(ternakList[position-1].created_at)
            if(tahun == tahunPrev){
                holder.thnTernak.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.onItemDetailClick(ternakList[position])
        }

    }

    override fun getItemCount(): Int {
      return ternakList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thnTernak = view.textViewTahunTani
        val kecTernak = view.textViewKecTernak
        val textViewSapiTernak = view.textViewSapiPotong
        val textViewKambing = view.textViewKombing
        val textViewAyamKapung = view.textViewAyamKampung
        val tglTernak = view.textViewAyamKampung

        fun bind(data: Peternakan) {
            val tgl = Tanggal.getOnlyYear(data.created_at)
            tglTernak.text = Tanggal.getTanggal(data.created_at)
            thnTernak.text = tgl
            kecTernak.text = data.kecamatan
            textViewSapiTernak.text = data.sapi_potong
            textViewKambing.text = data.kambing
            textViewAyamKapung.text = data.ayam_kampung
        }

    }

    interface  OnItemClickListener {
        fun onItemDetailClick(peternakan: Peternakan)
    }
}