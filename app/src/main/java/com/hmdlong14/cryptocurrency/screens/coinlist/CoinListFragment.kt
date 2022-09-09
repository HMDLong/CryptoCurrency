package com.hmdlong14.cryptocurrency.screens.coinlist

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.CoinRepository
import com.hmdlong14.cryptocurrency.data.repository.StateKey
import com.hmdlong14.cryptocurrency.data.repository.sources.local.LocalCoinSource
import com.hmdlong14.cryptocurrency.data.repository.sources.remote.RemoteCoinSource
import com.hmdlong14.cryptocurrency.databinding.FragmentCoinListBinding
import com.hmdlong14.cryptocurrency.screens.coinlist.CategoryAdapter.Companion.FAV_POS
import com.hmdlong14.cryptocurrency.screens.coinlist.CategoryAdapter.Companion.RANKING_POS
import com.hmdlong14.cryptocurrency.utils.base.BaseFragment

class CoinListFragment :
    BaseFragment<FragmentCoinListBinding>(FragmentCoinListBinding::inflate),
    ViewCoinsContract.View {

    private val presenter : CoinListPresenter by lazy {
        CoinListPresenter(
            CoinRepository.getInstance(
                LocalCoinSource(),
                RemoteCoinSource(),
            )
        )
    }

    private val coinListAdapter : CoinListAdapter by lazy { CoinListAdapter() }

    private val state = mutableMapOf<StateKey, Any>(
        StateKey.QUERY_TEXT to "",
        StateKey.CURR_CATEGORY to RANKING_POS
    )

    override fun initView() {
        binding.apply {
            rcvCategory.apply {
                adapter = CategoryAdapter().apply {
                    listener = object : CategoryClickListener {
                        override fun onCategoryClick(categoryPos: Int) {
                            state[StateKey.CURR_CATEGORY] = categoryPos
                            when(categoryPos){
                                FAV_POS -> this@CoinListFragment.context?.let {
                                    presenter.getFavoriteCoins(it.applicationContext)
                                }
                                else -> {
                                    presenter.getCoins(state)
                                }
                            }
                        }
                    }
                }
                layoutManager = LinearLayoutManager(this@CoinListFragment.context, RecyclerView.HORIZONTAL, false)
            }

            coinList.apply {
                adapter = coinListAdapter
                layoutManager = LinearLayoutManager(this@CoinListFragment.context, RecyclerView.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(this@CoinListFragment.context, RecyclerView.VERTICAL))
                addOnScrollListener(object : PaginationScrollListener() {
                    override fun loadMoreData() {
                        if(state[StateKey.CURR_CATEGORY] == FAV_POS)
                            return
                        coinListAdapter.addFooterLoading()
                        presenter.getMoreCoins(state)
                    }

                    override fun isLoading(): Boolean {
                        return coinListAdapter.isLoading
                    }

                    override fun isLastPage(): Boolean = false
                }.also { listener ->
                    listener.layoutManager = layoutManager as LinearLayoutManager
                })
            }

            searchBox.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            state[StateKey.QUERY_TEXT] = query.trim().replace(' ', '+')
                            presenter.searchCoins(state)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })

                setOnSearchClickListener {
                    binding.coinsTabTitle.visibility = View.GONE
                }

                setOnCloseListener {
                    binding.coinsTabTitle.visibility = View.VISIBLE
                    state[StateKey.QUERY_TEXT] = ""
                    presenter.getCoins(state)
                    false
                }
            }
        }
    }

    override fun onGetCoinsSuccess(coins: MutableList<Coin>) {
        coinListAdapter.removeFooterLoading()
        coinListAdapter.setCoins(coins)
    }

    override fun onGetMoreCoinsSuccess(coins: MutableList<Coin>) {
        coinListAdapter.removeFooterLoading()
        coinListAdapter.addCoins(coins)
    }

    override fun onGetCoinFailed(exception: Exception) {
        Toast.makeText(this.activity?.applicationContext, exception.message, Toast.LENGTH_LONG).show()
    }

    override fun initData() {
        presenter.setView(this)
        coinListAdapter.addFooterLoading()
        presenter.getCoins(state)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoinListFragment()
    }
}
