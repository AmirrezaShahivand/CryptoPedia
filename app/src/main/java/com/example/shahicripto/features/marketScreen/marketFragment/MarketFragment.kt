package com.example.shahicripto.features.marketScreen.marketFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.shahicripto.databinding.FragmentMarketBinding
import com.example.shahicripto.features.coinScreen.CoinActivity
import com.example.shahicripto.features.marketScreen.MarketScreenViewModel
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.CoinAboutData
import com.example.shahicripto.model.local.CoinAboutItem
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.MarketViewModelFactory
import com.example.shahicripto.util.showToast
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MarketFragment : Fragment(), MarketAdapter.RecyclerCallback {
    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!
    lateinit var marketScreenViewModel: MarketScreenViewModel
    lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        marketScreenViewModel = ViewModelProvider(
            this,
            MarketViewModelFactory(
                MainRepository(
                    apiService,
                    MyDatabase.getDatabase(view.context).coinsDataDao,
                    MyDatabase.getDatabase(view.context).newsDataDao,
                    MyDatabase.getDatabase(view.context).coinCatalogDao,
                    MyDatabase.getDatabase(view.context).priceSnapshotDao
                )
            )
        ).get(MarketScreenViewModel::class.java)




        getAboutDataFromAssest()
        initUi()

        binding.layoutWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://coinmarketcap.com/"))
            if (intent.resolveActivity(requireContext().packageManager) != null) startActivity(intent)
        }


        marketScreenViewModel.refreshData()


    }


    private fun getAboutDataFromAssest() {

        val fileInString = context?.assets
            ?.open("currencyinfo.json")
            ?.bufferedReader()
            .use { it?.readText() }

        aboutDataMap = mutableMapOf<String, CoinAboutItem>()

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinAboutData::class.java)

        dataAboutAll.forEach {
            aboutDataMap[it.currencyName] = CoinAboutItem(
                it.info.web,
                it.info.github,
                it.info.twt,
                it.info.desc,
                it.info.reddit
            )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initUi() {
        getTopCoinsDataBase()
    }

    override fun onItemClicked(dataCoin: CoinsDataEntitity) {

        val intent = Intent(context, CoinActivity::class.java)
//        intent.putExtra("sendToData", dataCoin)


        val bundle = Bundle()
        bundle.putSerializable("bundle1", dataCoin)
        bundle.putSerializable("bundle2", aboutDataMap[dataCoin.name])
        intent.putExtra("bundle", bundle)

        startActivity(intent)

    }


    private fun getTopCoinsDataBase() {

        marketScreenViewModel
            .getTopCoinsFromDataBase()
            .observe(viewLifecycleOwner) {
                showDataINRecycler(it)
            }

        marketScreenViewModel.getErrorData().observe(viewLifecycleOwner) {
            binding.progressMarket.visibility = View.GONE
            binding.txtMarketEmpty.text = it
            binding.txtMarketEmpty.visibility = View.VISIBLE
            binding.layoutWatchlist.recyclerMarket.announceForAccessibility(it)
            context?.showToast(it)
        }

    }

    private fun showDataINRecycler(data: List<CoinsDataEntitity>) {

        binding.progressMarket.visibility = View.GONE
        binding.txtMarketEmpty.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
        binding.layoutWatchlist.recyclerMarket.apply {
            if (layoutManager == null) layoutManager = LinearLayoutManager(context)
            adapter = MarketAdapter(glide, ArrayList(data), this@MarketFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
