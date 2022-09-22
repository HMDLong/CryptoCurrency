package com.hmdlong14.cryptocurrency.screens.coin.detail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
import kotlin.math.roundToInt

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
            txtPrice.text = activity?.resources?.getString(R.string.txt_price)?.format(coin.price)
            txtMarketCap.text = " $${coin.marketCap}"
            txtVolume24.text = " $${coin.volume24h}"
            arrayOf(txtLabelPrice, txtLabelDes).forEach { textView ->
                textView.setTextColor(Color.parseColor(coin.color))
            }
            root.background =  GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, getTriGradient(Color.parseColor(coin.color)))
//            val entries = arrayListOf<Entry>().apply {
//                var x = 0
//                val interval = 1000
//                coin.sparklines.forEach { sparkVal ->
//                    add(Entry(x.toFloat(), sparkVal.toFloat()))
//                    x += interval
//                }
//            }
//            priceChart.apply {
//                data = LineData(LineDataSet(entries, "Sparklines").applyCustomStyle())
//                applyCustomStyle()
//                invalidate()
//            }
        }
    }

    override fun onGetHistory(history: List<HistoryEntry<Double>>){
        val entries = arrayListOf<Entry>().apply {
            var x = 0
            history.forEach { (_, price) ->
                add(Entry(x.toFloat(), price.toFloat()))
                x += 1000
            }
        }
        binding.priceChart.apply {
            data = LineData(LineDataSet(entries, "Price").applyCustomStyle())
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