package com.hmdlong14.cryptocurrency.screens.coin

import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.databinding.FragmentCoinBinding
import com.hmdlong14.cryptocurrency.screens.coin.coinlist.CoinClickListener
import com.hmdlong14.cryptocurrency.screens.coin.coinlist.CoinListFragment
import com.hmdlong14.cryptocurrency.screens.coin.detail.CoinDetailFragment
import com.hmdlong14.cryptocurrency.utils.base.BaseFragment

private const val TAG_DETAIL = "detail"

class CoinFragment : BaseFragment<FragmentCoinBinding>(FragmentCoinBinding::inflate){

    override fun initView() {
        childFragmentManager.beginTransaction()
            .add(
                binding.fragmentFrame.id,
                CoinListFragment.newInstance(object : CoinClickListener {
                    override fun onCoinClick(coin: Coin) {
                        toDetailFragment(coin)
                    }
                }))
            .addToBackStack(null)
            .commit()
    }

    private fun toDetailFragment(coin: Coin){
        childFragmentManager.beginTransaction()
            .replace(
                binding.fragmentFrame.id,
                CoinDetailFragment.newInstance(coin)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun initData() {
        //TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoinFragment()
    }
}