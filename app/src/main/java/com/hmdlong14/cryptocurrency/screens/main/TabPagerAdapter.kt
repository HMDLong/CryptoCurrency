package com.hmdlong14.cryptocurrency.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hmdlong14.cryptocurrency.screens.coin.CoinFragment
import com.hmdlong14.cryptocurrency.screens.convert.ConverterFragment
import com.hmdlong14.cryptocurrency.screens.holdings.HoldingFragment

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = TAB_COUNT

    override fun createFragment(position: Int): Fragment =
        when(position){
            COINS_TAB_POS -> CoinFragment.newInstance()
            CONVERTER_TAB_POS -> ConverterFragment.newInstance()
            HOLDINGS_TAB_POS -> HoldingFragment.newInstance()
            else -> CoinFragment.newInstance()
        }

    companion object {
        const val TAB_COUNT = 3

        const val COINS_TAB_POS = 0
        const val CONVERTER_TAB_POS = 1
        const val HOLDINGS_TAB_POS = 2
    }
}
