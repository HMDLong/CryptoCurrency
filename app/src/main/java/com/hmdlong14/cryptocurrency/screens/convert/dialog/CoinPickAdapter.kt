package com.hmdlong14.cryptocurrency.screens.convert.dialog

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmdlong14.cryptocurrency.data.model.Coin
import com.hmdlong14.cryptocurrency.databinding.CoinPickItemBinding
import com.hmdlong14.cryptocurrency.utils.extensions.loadImage

class CoinPickAdapter : RecyclerView.Adapter<CoinPickAdapter.CoinPickHolder>() {

    private val coinList = mutableListOf<Coin>()
    private var dragItem : View? = null

    fun setCoins(coins : List<Coin>){
        coinList.apply {
            clear()
            addAll(coins)
        }
        notifyDataSetChanged()
    }

    fun addCoins(coins : List<Coin>){
        val addIdx = coinList.size
        coinList.addAll(coins)
        notifyItemRangeInserted(addIdx, coins.size)
    }

    fun dropFail(){
        dragItem?.visibility = View.VISIBLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinPickHolder {
        val binding = CoinPickItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinPickHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinPickHolder, position: Int) {
        holder.binding.apply {
            coinSymbol.text = coinList[position].symbol
            coinImage.loadImage(coinList[position].iconUrl)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                root.setOnLongClickListener {
                    dragItem = it
                    it.startDragAndDrop(
                        ClipData(
                            "abc",
                            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                            ClipData.Item(Intent().putExtra("data", coinList[position]))
                        ),
                        View.DragShadowBuilder(it),
                        null,
                        0
                    )
                    dragItem?.visibility = View.INVISIBLE
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int = coinList.size

    inner class CoinPickHolder(val binding: CoinPickItemBinding) : RecyclerView.ViewHolder(binding.root)
}

private class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {

    private val shadow = ColorDrawable(Color.LTGRAY)

    // Defines a callback that sends the drag shadow dimensions and touch point
    // back to the system.
    override fun onProvideShadowMetrics(size: Point, touch: Point) {

        // Set the width of the shadow to half the width of the original View.
        val width: Int = view.width / 2

        // Set the height of the shadow to half the height of the original View.
        val height: Int = view.height / 2

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the
        // same as the Canvas that the system provides. As a result, the drag shadow
        // fills the Canvas.
        shadow.setBounds(0, 0, width, height)

        // Set the size parameter's width and height values. These get back to
        // the system through the size parameter.
        size.set(width, height)

        // Set the touch point's position to be in the middle of the drag shadow.
        touch.set(width / 2, height / 2)
    }

    // Defines a callback that draws the drag shadow in a Canvas that the system
    // constructs from the dimensions passed to onProvideShadowMetrics().
    override fun onDrawShadow(canvas: Canvas) {

        // Draw the ColorDrawable on the Canvas passed in from the system.
        shadow.draw(canvas)
    }
}
