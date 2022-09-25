package com.hmdlong14.cryptocurrency.screens.coin.detail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.EntryXComparator
import com.hmdlong14.cryptocurrency.R
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.CoinRepository
import com.hmdlong14.cryptocurrency.data.repository.sources.local.LocalCoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.RemoteCoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.fetchJson.parser.HistoryEntry
import com.hmdlong14.cryptocurrency.databinding.FragmentCoinDetailBinding
import com.hmdlong14.cryptocurrency.utils.base.BaseFragment
import com.hmdlong14.cryptocurrency.utils.extensions.applyCustomStyle
import com.hmdlong14.cryptocurrency.utils.extensions.getTriGradient
import com.hmdlong14.cryptocurrency.utils.extensions.loadImage
import java.text.NumberFormat
import java.util.*

private const val ARG_COIN = "coin"

class CoinDetailFragment :
    BaseFragment<FragmentCoinDetailBinding>(FragmentCoinDetailBinding::inflate),
    CoinDetailContract.View {

    private var isDescriptionOpen = false

    private lateinit var coin: Coin
    private val presenter : CoinDetailPresenter by lazy {
        CoinDetailPresenter(
            CoinRepository.getInstance(
                LocalCoinSource(),
                RemoteCoinSource()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            coin = (it.getSerializable(ARG_COIN) as? Coin)!!
        }
    }

    override fun initView() {
        binding.apply {
            descriptionCard.setOnClickListener {
                coinDescription.updateLayoutParams {
                    height = if(isDescriptionOpen) DES_TEXT_COLLAPSE_HEIGHT else ViewGroup.LayoutParams.WRAP_CONTENT
                }
                isDescriptionOpen = !isDescriptionOpen
            }
            priceChart.applyCustomStyle()
            btnBack.setOnClickListener {
                this@CoinDetailFragment
            }
        }
    }

    override fun initData() {
        presenter.setView(this)
        presenter.getDetail(coin)
    }

    override fun onGetDetailSuccess(coin: Coin) {
        binding.apply {
            coinImg.loadImage(coin.iconUrl)
            coinName.text = coin.name
            coinSymbol.text = coin.symbol
            coinDescription.text = coin.description
            with(NumberFormat.getCurrencyInstance(Locale.US)){
                txtPrice.text = format(coin.price)
                txtMarketCap.text = format(coin.marketCap)
                txtVolume24.text = format(coin.volume24h)
            }
            arrayOf(txtLabelPrice, txtLabelDes).forEach { textView ->
                textView.setTextColor(Color.parseColor(coin.color))
            }
            root.background = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, getTriGradient(Color.parseColor(coin.color)))
        }
    }

    override fun onGetHistory(history: List<HistoryEntry<Double>>){
        val entries = arrayListOf<Entry>().apply {
            history.forEach { (timestamp, price) ->
                add(Entry(timestamp.toFloat(), price.toFloat()))
            }
            Collections.sort(this, EntryXComparator())
        }
        binding.priceChart.apply {
            data = LineData(this@CoinDetailFragment.context?.let {
                LineDataSet(entries, "Price").applyCustomStyle(it)
            })
            invalidate()
        }
    }

    override fun onFail(exception: Exception) {
        Toast.makeText(this.context?.applicationContext, exception.message, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(coin: Coin) =
            CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_COIN, coin)
                }
            }

        const val DES_TEXT_COLLAPSE_HEIGHT = 150
    }
}