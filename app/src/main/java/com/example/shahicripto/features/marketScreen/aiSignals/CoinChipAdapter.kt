package com.example.shahicripto.features.marketScreen.aiSignals

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.databinding.ItemCoinChipBinding
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.formatCryptoPriceText
import com.example.shahicripto.util.formatGroupedNumber
import java.util.Locale

class CoinChipAdapter(
    private val glide: RequestManager,
    private val coins: List<CoinsDataEntitity>,
    private var selectedIndex: Int = 0,
    private val onCoinSelected: (CoinsDataEntitity) -> Unit
) : RecyclerView.Adapter<CoinChipAdapter.CoinChipViewHolder>() {

    inner class CoinChipViewHolder(val binding: ItemCoinChipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinsDataEntitity, isSelected: Boolean) {
            binding.txtChipSymbol.text = coin.name
            binding.txtChipPrice.text = formatCryptoPriceText(coin.price)

            val changeColor = if (coin.change < 0) R.color.colorLoss else R.color.colorGain
            binding.txtChipChange.setTextColor(ContextCompat.getColor(binding.root.context, changeColor))
            binding.txtChipChange.text = "${formatGroupedNumber(coin.change)}%"

            val symbol = coin.name.lowercase(Locale.US)
            val coincapUrl = "https://assets.coincap.io/assets/icons/$symbol@2x.png"
            val spothqUrl = "https://cdn.jsdelivr.net/gh/spothq/cryptocurrency-icons@master/128/color/$symbol.png"

            val targetUrl = if (coin.url.isNotBlank() && !coin.url.contains("static.coinpaprika.com")) {
                coin.url
            } else {
                coincapUrl
            }

            glide.load(targetUrl)
                .placeholder(R.drawable.ic_coin_placeholder)
                .error(
                    glide.load(spothqUrl)
                        .placeholder(R.drawable.ic_coin_placeholder)
                        .error(R.drawable.ic_coin_placeholder)
                )
                .into(binding.imgChipCoin)

            val context = binding.root.context
            if (isSelected) {
                binding.cardCoinChip.strokeColor = ContextCompat.getColor(context, R.color.colorPrimary)
                binding.cardCoinChip.strokeWidth = (2 * context.resources.displayMetrics.density).toInt()
            } else {
                binding.cardCoinChip.strokeColor = ContextCompat.getColor(context, R.color.surfaceVariantColor)
                binding.cardCoinChip.strokeWidth = (1 * context.resources.displayMetrics.density).toInt()
            }

            binding.root.setOnClickListener {
                val prev = selectedIndex
                selectedIndex = bindingAdapterPosition
                notifyItemChanged(prev)
                notifyItemChanged(selectedIndex)
                onCoinSelected(coin)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinChipViewHolder {
        val binding = ItemCoinChipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinChipViewHolder(binding)
    }

    override fun getItemCount(): Int = coins.size

    override fun onBindViewHolder(holder: CoinChipViewHolder, position: Int) {
        holder.bind(coins[position], position == selectedIndex)
    }

    fun getSelectedCoin(): CoinsDataEntitity? {
        return if (coins.isNotEmpty() && selectedIndex in coins.indices) coins[selectedIndex] else null
    }
}
