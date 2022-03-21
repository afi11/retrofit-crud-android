package com.example.pertanian.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertanian.R
import com.example.pertanian.models.Kehutanan
import com.example.pertanian.models.Pertanian
import com.example.pertanian.utils.Tanggal
import kotlinx.android.synthetic.main.row_kehutanan.view.*
import kotlinx.android.synthetic.main.row_tani.view.*
import kotlinx.android.synthetic.main.row_tani.view.textViewProduksi

class KehutananRcyAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<KehutananRcyAdapter.ViewHolder>()  {

    var hutanList = mutableListOf<Kehutanan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KehutananRcyAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_kehutanan, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: KehutananRcyAdapter.ViewHolder, position: Int) {
        holder.bind(hutanList[position])
        if(position > 0){
            var tahun = Tanggal.getOnlyYear(hutanList[position].created_at)
            var tahunPrev = Tanggal.getOnlyYear(hutanList[position-1].created_at)
            if(tahun == tahunPrev){
                holder.thnHutan.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.onItemDetailClick(hutanList[position])
        }

    }

    override fun getItemCount(): Int {
      return hutanList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thnHutan = view.textViewTahunKehutanan
        val tanamanHutan = view.textViewTanamanHutan
        val produksiHutan = view.textViewProduksi
        val nilaiProduksi = view.textViewNilaiProduksi
        val tglHutan = view.textViewTglKehutanan

        fun bind(data: Kehutanan) {
            val tgl = Tanggal.getOnlyYear(data.created_at)
            tglHutan.text = Tanggal.getTanggal(data.created_at)
            thnHutan.text = tgl
            tanamanHutan.text = data.tanaman_hutan
            produksiHutan.text = data.produksi+" m3"
            nilaiProduksi.text = "Rp "+data.nilai_produksi
        }

    }

    interface  OnItemClickListener {
        fun onItemDetailClick(kehutanan: Kehutanan)
    }
}