package com.hmdlong14.cryptocurrency.screens.coinlist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener : RecyclerView.OnScrollListener() {
    lateinit var layoutManager: LinearLayoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dy > 0) {
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            if (isLoading() || isLastPage()) {
                return
            }
            if (firstVisiblePosition >= 0 && layoutManager.childCount + firstVisiblePosition >= layoutManager.itemCount) {
                loadMoreData()
            }
        }
    }

    abstract fun loadMoreData()
    abstract fun isLoading() : Boolean
    abstract fun isLastPage() : Boolean
}
