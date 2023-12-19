package com.example.shahicripto.features.marketScreen.marketFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.util.BASE_URL_IMAGE
import com.example.shahicripto.databinding.ItemRecyclerMarketBinding
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity


class MarketAdapter(
    private val glide: RequestManager ,
    private val data: ArrayList<CoinsDataEntitity>,
    private val recyclerCallback: RecyclerCallback
) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    lateinit var binding: ItemRecyclerMarketBinding



    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindView(dataCoin: CoinsDataEntitity) {


            if (dataCoin.cHANGE24HOUR!=null && dataCoin.change!=null && dataCoin.url!=null
                && dataCoin.hajm!=null && dataCoin.price!=null && dataCoin.fullName != null
                && dataCoin.cHANGE24HOUR_RAW!=null && dataCoin.hIGH24HOUR!=null
                && dataCoin.mKTCAP!=null && dataCoin.cHANGEPCT24HOUR !=null &&
                dataCoin.lOW24HOUR != null && dataCoin.oPEN24HOUR!=null &&
                dataCoin.tOTALVOLUME24H != null && dataCoin.algorithm!=null &&
                dataCoin.sUPPLY != null && dataCoin.price !=null && dataCoin.fullName!=null
                ){
                binding.txtNameCoin.text = dataCoin.name
                binding.txtPrice.text = dataCoin.price


                val taghir = dataCoin.change

                if (taghir > 0) {
                    binding.txtTaghir.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorGain
                        )
                    )
                    binding.txtTaghir.text = dataCoin.change.toString().substring(0, 4) + "%"
                } else if (taghir < 0) {
                    binding.txtTaghir.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorLoss
                        )
                    )
                    binding.txtTaghir.text = dataCoin.change.toString().substring(0, 4)+ "%"
                } else {
                    binding.txtTaghir.text = "0%"
                }


                val hajm = dataCoin.hajm.div(1000000000)
                val indexDot = hajm.toString().indexOf('.')
                binding.txtHajm.text = hajm.toString().substring(0, indexDot + 2) + "B"





                // Hilt =>
                glide
                    .load(BASE_URL_IMAGE + dataCoin.url)
                    .into(binding.imgCoin)

//            Glide
//                .with(itemView)
//                .load(BASE_URL_IMAGE + dataCoin.url)
//                .into(binding.imgCoin)




            }


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
        fun onItemClicked(dataCoin: CoinsDataEntitity)

    }


}