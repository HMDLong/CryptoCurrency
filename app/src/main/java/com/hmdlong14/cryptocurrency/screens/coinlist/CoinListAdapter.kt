package com.hmdlong14.cryptocurrency.screens.coinlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.hmdlong14.cryptocurrency.R
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.data.repository.sources.local.favorites.FavoritesSource
import com.hmdlong14.cryptocurrency.databinding.CoinItemBinding
import com.hmdlong14.cryptocurrency.databinding.LoadingItemBinding
import com.hmdlong14.cryptocurrency.utils.extensions.loadImage
import com.hmdlong14.cryptocurrency.utils.extensions.round
import com.hmdlong14.cryptocurrency.utils.extensions.roundUpToBn
import java.text.NumberFormat
import java.util.*


object HolderType {
    const val LOADING = 0
    const val ITEM = 1
}

class CoinListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val coinList = mutableListOf<Coin>()
    var isLoading = false

    fun setCoins(coins : MutableList<Coin>){
        coinList.apply {
            clear()
            addAll(coins)
        }
        notifyDataSetChanged()
    }

    fun addCoins(coins: MutableList<Coin>){
        val insertPos = coinList.size
        coinList.addAll(coins)
        notifyItemRangeInserted(insertPos, coins.size)
    }

    override fun getItemCount(): Int = coinList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            HolderType.ITEM -> {
                val binding = CoinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CoinHolder(binding.root, binding)
            }
            HolderType.LOADING -> {
                val binding = LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingHolder(binding.root, binding)
            }
            else -> {
                val binding = CoinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CoinHolder(binding.root, binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(coinList.size != 0 && position == coinList.size - 1 && isLoading){
            return HolderType.LOADING
        }
        return HolderType.ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CoinHolder -> setupItemHolder(holder, position)
            is LoadingHolder -> setupLoadingHolder(holder)
        }
    }

    private fun setupLoadingHolder(holder: LoadingHolder) {
        holder.binding.progressBar.animate()
    }

    private fun setupItemHolder(holder: CoinHolder, position: Int) {
        holder.binding.apply {
            coinList[position].let { coin ->
                coinImg.loadImage(coin.iconUrl)
                coinName.text = coin.name
                coinSymbol.text = coin.symbol
                marketCap.text = getMarketCapText(coin.marketCap)
                setChangeText(change, coin.change)
                price.text = getPriceText(coin.price)
                favoriteButton.setImageDrawable(
                    AppCompatResources.getDrawable(holder.itemView.context,
                        if(FavoritesSource.getInstance(holder.itemView.context.applicationContext).isFavorite(coin.uuid)){
                            R.drawable.ic_star_fav
                        } else {
                            R.drawable.ic_star_unfav
                        }
                    )
                )
                favoriteButton.setOnClickListener {
                    changeOnFavButtonClick(holder, coinList[position])
                }
            }
        }
    }

    private fun changeOnFavButtonClick(holder: CoinHolder, coin: Coin){
        FavoritesSource.getInstance(holder.itemView.context.applicationContext).apply {
            if(isFavorite(coin.uuid)){
                holder.binding.favoriteButton.setImageDrawable(
                    AppCompatResources.getDrawable(holder.itemView.context, R.drawable.ic_star_unfav)
                )
                removeFromFavorites(coin.uuid)
            } else {
                holder.binding.favoriteButton.setImageDrawable(
                    AppCompatResources.getDrawable(holder.itemView.context, R.drawable.ic_star_fav)
                )
                addToFavorites(coin.uuid)
            }
        }
    }

    private fun setChangeText(tvChange : TextView, change : Double?) {
        if(change == null){
            tvChange.text = "Unidentified"
        } else {
            tvChange.text = change.toString()
            tvChange.setTextColor(tvChange.resources.getColor(
                if(change < 0) {
                    R.color.color_red
                } else {
                    R.color.color_teal_200
                }
            ))
        }
    }

    private fun getMarketCapText(marketCap : Long?) : String {
        if(marketCap == null)
            return "Unidentified"
        return StringBuilder().append("M.Cap ")
            .append(
                NumberFormat.getCurrencyInstance(Locale.US).format(marketCap.roundUpToBn())
            )
            .append(" Bn")
            .toString()
    }

    private fun getPriceText(price : Double?) : String {
        if(price == null)
            return "Unidentified"
        return StringBuilder().append(
                NumberFormat.getCurrencyInstance(Locale.US).format(price.round(2))
            ).toString()
    }

    fun addFooterLoading(){
        isLoading = true
        coinList.add(Coin())
        notifyItemInserted(coinList.size - 1)
    }

    fun removeFooterLoading(){
        isLoading = false
        coinList.removeLast()
        notifyItemRemoved(coinList.size - 1)
    }

    inner class CoinHolder(itemView : View, val binding : CoinItemBinding) : RecyclerView.ViewHolder(itemView)

    inner class LoadingHolder(itemView: View, val binding: LoadingItemBinding) : RecyclerView.ViewHolder(itemView)
}
