package com.hmdlong14.cryptocurrency

import androidx.viewpager2.widget.ViewPager2
import com.hmdlong14.cryptocurrency.databinding.ActivityMainBinding
import com.hmdlong14.cryptocurrency.screens.main.TabPagerAdapter
import com.hmdlong14.cryptocurrency.screens.main.TabPagerAdapter.Companion.COINS_TAB_POS
import com.hmdlong14.cryptocurrency.screens.main.TabPagerAdapter.Companion.CONVERTER_TAB_POS
import com.hmdlong14.cryptocurrency.screens.main.TabPagerAdapter.Companion.HOLDINGS_TAB_POS
import com.hmdlong14.cryptocurrency.utils.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initView() {
        binding.apply {
            pager.apply {
                adapter = TabPagerAdapter(this@MainActivity)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        when(position){
                            COINS_TAB_POS -> bottomNavbar.menu.findItem(R.id.coins_tab).isChecked = true
                            CONVERTER_TAB_POS -> bottomNavbar.menu.findItem(R.id.converter_tab).isChecked = true
                            HOLDINGS_TAB_POS -> bottomNavbar.menu.findItem(R.id.holdings_tab).isChecked = true
                        }
                    }
                })
                isUserInputEnabled = false
            }

            bottomNavbar.setOnItemSelectedListener { menuItem ->
                pager.currentItem =
                    when(menuItem.itemId){
                        R.id.coins_tab -> COINS_TAB_POS
                        R.id.converter_tab -> CONVERTER_TAB_POS
                        R.id.holdings_tab -> HOLDINGS_TAB_POS
                        else -> COINS_TAB_POS
                    }
                true
            }
        }
    }

    override fun initData() {
        //TODO implement later
    }
}
