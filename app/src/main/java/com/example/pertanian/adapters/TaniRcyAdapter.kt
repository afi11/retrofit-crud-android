package com.example.pertanian.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertanian.R
import com.example.pertanian.models.Pertanian
import com.example.pertanian.utils.Tanggal
import kotlinx.android.synthetic.main.row_tani.view.*

class TaniRcyAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<TaniRcyAdapter.ViewHolder>()  {

    var taniList = mutableListOf<Pertanian>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaniRcyAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_tani, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TaniRcyAdapter.ViewHolder, position: Int) {
        holder.bind(taniList[position])
        if(position > 0){
            var tahun = Tanggal.getOnlyYear(taniList[position].created_at)
            var tahunPrev = Tanggal.getOnlyYear(taniList[position-1].created_at)
            if(tahun == tahunPrev){
                holder.thnTani.visibility = View.GONE
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.onItemDetailClick(taniList[position])
        }

    }

    override fun getItemCount(): Int {
      return taniList.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val thnTani = view.textViewTahunTani
        val kecTani = view.textViewKecTani
        val luasPanenTani = view.textViewLuasPanen
        val produksiTani = view.textViewProduksi
        val rata2Produksi = view.textViewRata2
        val tglTani = view.textViewTglTani

        fun bind(data: Pertanian) {
            val tgl = Tanggal.getOnlyYear(data.created_at)
            tglTani.text = Tanggal.getTanggal(data.created_at)
            thnTani.text = tgl
            kecTani.text = data.kecamatan
            luasPanenTani.text = data.luas_panen
            produksiTani.text = data.produksi
            rata2Produksi.text = data.rata_rata
        }

    }

    interface  OnItemClickListener {
        fun onItemDetailClick(pertanian: Pertanian)
    }
}