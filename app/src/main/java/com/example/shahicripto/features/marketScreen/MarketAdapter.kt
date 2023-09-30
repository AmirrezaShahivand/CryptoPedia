package com.example.shahicripto.features.marketScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shahicripto.R
import com.example.shahicripto.util.BASE_URL_IMAGE
import com.example.shahicripto.apiManager.model.CoinsData
import com.example.shahicripto.databinding.ItemRecyclerMarketBinding

class MarketAdapter(
    private val data: ArrayList<CoinsData.Data>,
    private val recyclerCallback: RecyclerCallback
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    lateinit var binding: ItemRecyclerMarketBinding

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindView(dataCoin: CoinsData.Data) {
            binding.txtNameCoin.text = dataCoin.coinInfo.name
            binding.txtPrice.text = dataCoin.dISPLAY.uSDT.pRICE


            val taghir = dataCoin.rAW.uSDT.cHANGE24HOUR

            if (taghir > 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorGain
                    )
                )
                binding.txtTaghir.text = dataCoin.dISPLAY.uSDT.cHANGEPCT24HOUR.substring(0, 4) + "%"
            } else if (taghir < 0) {
                binding.txtTaghir.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorLoss
                    )
                )
                binding.txtTaghir.text = dataCoin.dISPLAY.uSDT.cHANGEPCT24HOUR.substring(0, 4)+ "%"
            } else {
                binding.txtTaghir.text = "0%"
            }


            val hajm = dataCoin.rAW.uSDT.mKTCAP / 1000000000
            val indexDot = hajm.toString().indexOf('.')
            binding.txtHajm.text = hajm.toString().substring(0, indexDot + 2) + "B"






            Glide
                .with(itemView)
                .load(BASE_URL_IMAGE + dataCoin.coinInfo.imageUrl)
                .into(binding.imgCoin)

            itemView.setOnClickListener {
                recyclerCallback.onItemClicked(dataCoin)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        binding =
            ItemRecyclerMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding.root)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {

        holder.bindView(data[position])

    }


    interface RecyclerCallback {
        fun onItemClicked(dataCoin: CoinsData.Data)

    }


}