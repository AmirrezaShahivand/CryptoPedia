package com.example.shahicripto.model

import androidx.lifecycle.LiveData
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.api.CoinPaprikaTicker
import com.example.shahicripto.model.api.CoinGeckoMarketCoin
import com.example.shahicripto.model.local.ChartData
import com.example.shahicripto.model.local.CoinsData.CoinCatalogDao
import com.example.shahicripto.model.local.CoinsData.CoinCatalogEntity
import com.example.shahicripto.model.local.CoinsData.CoinsDataDao
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.model.local.CoinsData.PriceSnapshotDao
import com.example.shahicripto.model.local.CoinsData.PriceSnapshotEntity
import com.example.shahicripto.model.local.NewsData.NewsDataDao
import com.example.shahicripto.model.local.NewsData.NewsDataEntity
import com.example.shahicripto.util.ALL
import com.example.shahicripto.util.COINPAPRIKA_IMAGE_BASE_URL
import com.example.shahicripto.util.HOUR
import com.example.shahicripto.util.HOURS24
import com.example.shahicripto.util.MONTH
import com.example.shahicripto.util.MONTH3
import com.example.shahicripto.util.WEEK
import com.example.shahicripto.util.YEAR
import com.example.shahicripto.util.RssNewsParser
import com.example.shahicripto.util.formatCryptoPrice
import com.example.shahicripto.util.formatGroupedNumber
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Observable
import java.util.Locale

class MainRepository(
    private val apiService: ApiService,
    private val coinsDataDao: CoinsDataDao,
    private val newsDataDao: NewsDataDao,
    private val coinCatalogDao: CoinCatalogDao,
    private val priceSnapshotDao: PriceSnapshotDao
) {

    fun getCoinsList(): LiveData<List<CoinsDataEntitity>> = coinsDataDao.getAllCoins()

    fun getNews(): LiveData<List<NewsDataEntity>> = newsDataDao.getAllNews()

    fun refreshData(): Completable {
        return ensureCoinCatalog()
            .andThen(
                apiService.getCoinTicker("usdt-tether")
                    .flatMap { usdtTicker ->
                        apiService.getTopCoins().map { tickers ->
                            saveMarketData(tickers, usdtTicker)
                        }
                    }
                    .ignoreElement()
            )
            .onErrorResumeNext { refreshDataFromCoinGecko() }
    }

    private fun refreshDataFromCoinGecko(): Completable {
        return apiService.getCoinGeckoMarkets()
            .map { coins -> saveCoinGeckoMarketData(coins) }
            .ignoreElement()
    }

    private fun saveCoinGeckoMarketData(coins: List<CoinGeckoMarketCoin>) {
        if (coins.isEmpty()) throw IllegalStateException("No market data is available")

        val now = System.currentTimeMillis()
        val snapshotsAllowedAfter = now - SNAPSHOT_INTERVAL_MS
        coinsDataDao.clearAll()

        coins.forEach { coin ->
            val price = coin.currentPrice ?: return@forEach
            val change = coin.priceChangePercentage24h ?: 0.0
            val volume = coin.totalVolume ?: 0.0
            val marketCap = coin.marketCap ?: 0.0
            val entity = CoinsDataEntitity(
                coinId = coin.id,
                name = coin.symbol.uppercase(Locale.US),
                price = formatCryptoPrice(price),
                change = change,
                hajm = volume,
                url = coin.image.orEmpty(),
                oPEN24HOUR = "—",
                hIGH24HOUR = "—",
                lOW24HOUR = "—",
                cHANGE24HOUR = formatCryptoPrice(price * change / 100.0),
                algorithm = "—",
                tOTALVOLUME24H = formatNumber(volume),
                mKTCAP = formatNumber(marketCap),
                sUPPLY = coin.totalSupply?.let(::formatNumber) ?: "—",
                fullName = coin.name,
                cHANGEPCT24HOUR = formatPercent(change),
                cHANGE24HOUR_RAW = change
            )
            coinsDataDao.insertAll(entity)

            val latest = priceSnapshotDao.getLatestTimestamp(coin.id) ?: 0L
            if (latest < snapshotsAllowedAfter) {
                priceSnapshotDao.insert(
                    PriceSnapshotEntity(coin.id, now, price, volume, marketCap)
                )
            }
        }
    }

    private fun ensureCoinCatalog(): Completable {
        return Completable.defer {
            if (coinCatalogDao.count() > 0) {
                Completable.complete()
            } else {
                apiService.getCoinCatalog()
                    .map { coins ->
                        coins.map {
                            CoinCatalogEntity(
                                coinId = it.id,
                                name = it.name,
                                symbol = it.symbol,
                                rank = it.rank,
                                isActive = it.isActive,
                                type = it.type
                            )
                        }
                    }
                    .doOnSuccess { coinCatalogDao.insertAll(it) }
                    .ignoreElement()
            }
        }
    }

    private fun saveMarketData(tickers: List<CoinPaprikaTicker>, usdtTicker: CoinPaprikaTicker) {
        if (tickers.isEmpty()) {
            throw IllegalStateException("CoinPaprika returned no market data")
        }

        val usdtUsd = usdtTicker.quotes?.get("USD")?.price
            ?.takeIf { it > 0.0 } ?: 1.0
        val now = System.currentTimeMillis()
        val snapshotsAllowedAfter = now - SNAPSHOT_INTERVAL_MS
        coinsDataDao.clearAll()

        val marketData = tickers.mapNotNull { ticker ->
            val quote = ticker.quotes?.get("USD") ?: return@mapNotNull null
            val priceUsdt = (quote.price ?: return@mapNotNull null) / usdtUsd
            val change = quote.percentChange24h ?: 0.0
            val volumeUsdt = (quote.volume24h ?: 0.0) / usdtUsd
            val marketCapUsdt = (quote.marketCap ?: 0.0) / usdtUsd

            val entity = CoinsDataEntitity(
                coinId = ticker.id,
                name = ticker.symbol,
                price = formatPrice(priceUsdt),
                change = change,
                hajm = volumeUsdt,
                url = "$COINPAPRIKA_IMAGE_BASE_URL${ticker.id}/logo.png",
                oPEN24HOUR = "—",
                hIGH24HOUR = "—",
                lOW24HOUR = "—",
                cHANGE24HOUR = formatPrice(priceUsdt * change / 100.0),
                algorithm = "—",
                tOTALVOLUME24H = formatNumber(volumeUsdt),
                mKTCAP = formatNumber(marketCapUsdt),
                sUPPLY = ticker.totalSupply?.let(::formatNumber) ?: "—",
                fullName = ticker.name,
                cHANGEPCT24HOUR = formatPercent(change),
                cHANGE24HOUR_RAW = change
            )

            coinsDataDao.insertAll(entity)
            val latest = priceSnapshotDao.getLatestTimestamp(ticker.id) ?: 0L
            if (latest < snapshotsAllowedAfter) {
                priceSnapshotDao.insert(
                    PriceSnapshotEntity(
                        coinId = ticker.id,
                        timestamp = now,
                        priceUsdt = priceUsdt,
                        volumeUsdt = volumeUsdt,
                        marketCapUsdt = marketCapUsdt
                    )
                )
            }
            entity
        }

        if (marketData.isEmpty()) {
            throw IllegalStateException("CoinPaprika returned unusable market data")
        }
    }

    fun refreshDataNews(): Completable {
        val feeds = listOf(
            "https://cointelegraph.com/rss",
            "https://www.newsbtc.com/feed/",
            "https://decrypt.co/feed"
        )
        return Observable.fromIterable(feeds)
            .concatMapSingle { url ->
                apiService.getNewsFeed(url)
                    .map { RssNewsParser.parse(it.string()) }
                    .onErrorReturnItem(emptyList())
            }
            .flatMapIterable { it }
            .distinct { it.url }
            .toList()
            .doOnSuccess { data ->
                if (data.isEmpty()) throw IllegalStateException("No news feeds are available")
                newsDataDao.clearAll()
                data.take(60).forEach(newsDataDao::insertAll)
            }
            .ignoreElement()
    }

    fun getChartData(coinId: String, symbol: String, period: String): Single<ChartData> {
        val now = System.currentTimeMillis()
        val chartConfig = when (period) {
            HOUR -> ChartConfig(now - 12L * 60 * 60 * 1000, "1h", 12)
            HOURS24 -> ChartConfig(now - 24L * 60 * 60 * 1000, "1h", 24)
            WEEK -> ChartConfig(now - 7L * 24 * 60 * 60 * 1000, "4h", 42)
            MONTH -> ChartConfig(now - 30L * 24 * 60 * 60 * 1000, "1d", 30)
            MONTH3 -> ChartConfig(now - 90L * 24 * 60 * 60 * 1000, "1d", 90)
            YEAR -> ChartConfig(now - 365L * 24 * 60 * 60 * 1000, "1w", 52)
            ALL -> ChartConfig(0L, "1w", 200)
            else -> ChartConfig(now - 12L * 60 * 60 * 1000, "1h", 12)
        }

        return Single.fromCallable {
            priceSnapshotDao.getSince(coinId, chartConfig.from)
        }.flatMap { snapshots ->
            // Two local snapshots only produce a visually misleading straight
            // segment. Prefer real candle data until local history is useful.
            if (snapshots.size >= 3) {
                Single.just(buildChartData(snapshots.map { it.toChartPoint() }))
            } else {
                val binanceSymbol = "${symbol.uppercase(Locale.US)}USDT"
                apiService.getPublicKlines(
                    symbol = binanceSymbol,
                    interval = chartConfig.interval,
                    limit = chartConfig.limit
                ).map { rows ->
                    val points = rows.mapNotNull(::toChartPoint)
                    if (points.isEmpty()) {
                        throw IllegalStateException("برای این ارز داده نمودار موجود نیست")
                    }
                    buildChartData(points)
                }.onErrorResumeNext {
                    apiService.getTodayOhlc(coinId).map { values ->
                        val points = values.flatMap { value ->
                            val close = value.close ?: return@flatMap emptyList()
                            val open = value.open ?: close
                            val high = value.high ?: close
                            val low = value.low ?: close
                            val volume = value.volume ?: 0.0
                            listOf(
                                ChartData.Data(
                                    close = open,
                                    conversionSymbol = "USDT",
                                    conversionType = "direct",
                                    high = high,
                                    low = low,
                                    open = open,
                                    time = parseTime(value.timeOpen),
                                    volumefrom = volume,
                                    volumeto = volume
                                ),
                                ChartData.Data(
                                    close = close,
                                    conversionSymbol = "USDT",
                                    conversionType = "direct",
                                    high = high,
                                    low = low,
                                    open = open,
                                    time = parseTime(value.timeClose ?: value.timeOpen),
                                    volumefrom = volume,
                                    volumeto = volume
                                )
                            )
                        }
                        if (points.isEmpty()) {
                            throw IllegalStateException("برای این ارز داده نمودار موجود نیست")
                        }
                        buildChartData(points)
                    }
                }.onErrorReturn {
                    // Some symbols (for example USDT itself) do not have a
                    // Binance USDT pair. Keep the chart usable from local
                    // snapshots instead of surfacing CoinPaprika's 402.
                    if (snapshots.isNotEmpty()) {
                        buildChartData(snapshots.map { it.toChartPoint() })
                    } else {
                        throw IllegalStateException("برای این ارز داده نمودار موجود نیست")
                    }
                }
            }
        }
    }

    private fun toChartPoint(row: List<Double>): ChartData.Data? {
        if (row.size < 8) return null
        return ChartData.Data(
            close = row[4],
            conversionSymbol = "USDT",
            conversionType = "direct",
            high = row[2],
            low = row[3],
            open = row[1],
            time = (row[0].toLong() / 1000L)
                .coerceAtMost(Int.MAX_VALUE.toLong()).toInt(),
            volumefrom = row[5],
            volumeto = row[7]
        )
    }

    private fun PriceSnapshotEntity.toChartPoint(): ChartData.Data {
        return ChartData.Data(
            close = priceUsdt,
            conversionSymbol = "USDT",
            conversionType = "direct",
            high = priceUsdt,
            low = priceUsdt,
            open = priceUsdt,
            time = (timestamp / 1000L)
                .coerceAtMost(Int.MAX_VALUE.toLong()).toInt(),
            volumefrom = volumeUsdt,
            volumeto = volumeUsdt
        )
    }

    private fun buildChartData(values: List<ChartData.Data>): ChartData {
        val drawableValues = if (values.size == 1) {
            values + values.first().copy(time = values.first().time + 1)
        } else {
            values
        }
        return ChartData(
            aggregated = false,
            conversionType = ChartData.ConversionType("USDT", "direct"),
            data = drawableValues,
            firstValueInArray = true,
            response = "Success",
            timeFrom = drawableValues.first().time,
            timeTo = drawableValues.last().time,
            type = 0
        )
    }

    private fun parseTime(value: String?): Int {
        return value?.let {
            runCatching { java.time.Instant.parse(it).epochSecond }
                .getOrNull()
                ?.coerceAtMost(Int.MAX_VALUE.toLong())
                ?.toInt()
        } ?: (System.currentTimeMillis() / 1000L)
            .coerceAtMost(Int.MAX_VALUE.toLong())
            .toInt()
    }

    fun getCoinDetails(coinId: String) = apiService.getCoinDetails(coinId)
        .onErrorReturnItem(
            com.example.shahicripto.model.api.CoinPaprikaCoinDetails(
                id = coinId,
                name = "",
                symbol = "",
                logo = null,
                description = null,
                links = null
            )
        )

    fun getTodayOhlc(coinId: String) = apiService.getTodayOhlc(coinId)
        .zipWith(apiService.getCoinTicker("usdt-tether")) { values, usdtTicker ->
            val usdtUsd = usdtTicker.quotes?.get("USD")?.price
                ?.takeIf { it > 0.0 } ?: 1.0
            values.map { value ->
                value.copy(
                    open = value.open?.div(usdtUsd),
                    high = value.high?.div(usdtUsd),
                    low = value.low?.div(usdtUsd),
                    close = value.close?.div(usdtUsd),
                    volume = value.volume?.div(usdtUsd),
                    marketCap = value.marketCap?.div(usdtUsd)
                )
            }
        }
        .onErrorReturnItem(emptyList())

    private fun formatPrice(value: Double): String = formatCryptoPrice(value)

    private fun formatNumber(value: Double): String = formatGroupedNumber(value)

    private fun formatPercent(value: Double): String = formatGroupedNumber(value)

    companion object {
        private const val SNAPSHOT_INTERVAL_MS = 5L * 60 * 1000
    }

    private data class ChartConfig(
        val from: Long,
        val interval: String,
        val limit: Int
    )
}
