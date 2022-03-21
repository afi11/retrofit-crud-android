package com.example.pertanian.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertanian.R
import com.example.pertanian.models.Perikanan
import com.example.pertanian.utils.Tanggal
import kotlinx.android.synthetic.main.row_perikanan.view.*

class TernakPerikananAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<TernakPerikananAdapter.ViewHolder>()  {

    var perikananList = mutableListOf<Perikanan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TernakPerikananAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_perikanan, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TernakPerikananAdapter.ViewHolder, position: Int) {
        holder.bind(perikananList[position])
        if(position > 0){
            var tahun = Tanggal.getOnlyYear(perikananList[position].created_at)
            var tahunPrev = Tanggal.getOnlyYear(perikananList[position-1].created_at)
            if(tahun == tahunPrev){
                holder.thnPerikanan.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.onItemDetailClick(perikananList[position])
        }

    }

    override fun getItemCount(): Int {
      return perikananList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thnPerikanan = view.textViewTahunPerikanan
        val kecPerikanan = view.textViewKecPerikanan
        val textViewPerairan = view.textViewPerairan
        val textViewBudiDaya = view.textViewBudiDaya
        val textViewPembenihan = view.textViewPembenihan
        val tglPerikanan = view.textViewTglPerikanan

        fun bind(data: Perikanan) {
            val tgl = Tanggal.getOnlyYear(data.created_at)
            tglPerikanan.text = Tanggal.getTanggal(data.created_at)
            thnPerikanan.text = tgl
            kecPerikanan.text = data.kecamatan
            textViewPerairan.text = data.perairan_umum
            textViewBudiDaya.text = data.budidaya_kolam
            textViewPembenihan.text = data.pembenihan
        }

    }

    interface  OnItemClickListener {
        fun onItemDetailClick(perikanan: Perikanan)
    }
}