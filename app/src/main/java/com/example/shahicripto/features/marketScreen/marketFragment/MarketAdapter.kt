package com.example.shahicripto.features.marketScreen.marketFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.databinding.ItemRecyclerMarketBinding
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.formatGroupedNumber
import com.example.shahicripto.util.formatCryptoPriceText
class MarketAdapter(
    private val glide: RequestManager ,
    private val data: ArrayList<CoinsDataEntitity>,
    private val recyclerCallback: RecyclerCallback
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    inner class MarketViewHolder(private val binding: ItemRecyclerMarketBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(dataCoin: CoinsDataEntitity) {
            binding.txtNameCoin.text = dataCoin.name
            binding.txtPrice.text = formatCryptoPriceText(dataCoin.price)

            val changeColor = if (dataCoin.change < 0) R.color.colorLoss else R.color.colorGain
            binding.txtTaghir.setTextColor(ContextCompat.getColor(binding.root.context, changeColor))
            binding.txtTaghir.text = "${formatGroupedNumber(dataCoin.change)}%"
            binding.txtHajm.text = "${formatGroupedNumber(dataCoin.hajm / 1_000_000_000.0)}B"

            glide.load(dataCoin.url).into(binding.imgCoin)
            binding.root.setOnClickListener {
                recyclerCallback.onItemClicked(dataCoin)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {

        val binding = ItemRecyclerMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {

        holder.bindView(data[position])

    }


    interface RecyclerCallback {
        fun onItemClicked(dataCoin: CoinsDataEntitity)

    }


}
