package com.hmdlong14.cryptocurrency.screens.convert

import android.view.DragEvent
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.CoinRepository
import com.hmdlong14.cryptocurrency.data.repository.sources.local.LocalCoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.RemoteCoinSource
import com.hmdlong14.cryptocurrency.databinding.FragmentConverterBinding
import com.hmdlong14.cryptocurrency.screens.convert.dialog.CoinPickAdapter
import com.hmdlong14.cryptocurrency.utils.base.BaseFragment
import com.hmdlong14.cryptocurrency.utils.extensions.loadImage

class ConverterFragment :
    BaseFragment<FragmentConverterBinding>(FragmentConverterBinding::inflate),
    ConverterContract.View {

    private val coinPickAdapter : CoinPickAdapter by lazy { CoinPickAdapter() }

    private val presenter = ConverterPresenter(
        CoinRepository.getInstance(
            LocalCoinSource(),
            RemoteCoinSource()
        )
    )

    override fun initView() {
        val bottomSheetBinding = binding.botSheet
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetBinding.root)
        binding.sourceCoin.root.setOnClickListener {
            if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.sourceCoin.root.setOnDragListener { v, event ->
            when(event.action){
                DragEvent.ACTION_DROP -> {
                    (event.clipData.getItemAt(0).intent.getSerializableExtra("data") as Coin?)?.let {
                        presenter.setSource(it)
                    }
                }
            }
            true
        }

        binding.root.setOnDragListener { v, event ->
            when(event.action){
                DragEvent.ACTION_DROP -> coinPickAdapter.dropFail()
            }
            true
        }

        binding.inputText.doAfterTextChanged {
            try {
                presenter.convert(it.toString().toDouble())
            } catch (e : Exception) {
                binding.outputText.text = "0.0"
            }
        }
        binding.botSheet.pickList.adapter = coinPickAdapter
    }

    override fun initData() {
        presenter.view = this
        presenter.init()
    }

    override fun onSetCoinSuccess(source: Coin?, target: Coin?, coins: List<Coin>?) {
        source?.apply {
            binding.sourceCoin.apply {
                coinImage.loadImage(source.iconUrl)
                coinSymbol.text = source.symbol
            }
        }

        target?.apply {
            binding.targetCoin.apply {
                coinImage.loadImage(target.iconUrl)
                coinSymbol.text = target.symbol
            }
        }

        coins?.apply {
            coinPickAdapter.setCoins(coins)
        }
    }

    override fun onSetCoinFailed(exception: Exception) {
        Toast.makeText(this.context, exception.message, Toast.LENGTH_LONG).show()
    }

    override fun onResultSuccess(result: Double) {
        binding.outputText.text = result.toString()
    }

    override fun onResultFailed(exception: Exception) {
        Toast.makeText(this.context, exception.message, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConverterFragment()
    }
}
