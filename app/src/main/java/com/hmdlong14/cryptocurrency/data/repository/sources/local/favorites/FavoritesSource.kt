package com.hmdlong14.cryptocurrency.data.repository.sources.local.favorites

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class FavoritesSource private constructor() {

    private val _favorites = mutableListOf<String>()
    val favorites
        get() = _favorites.toList()
    lateinit var editor: SharedPreferences.Editor

    fun isFavorite(uuid: String) : Boolean = _favorites.contains(uuid)

    fun addToFavorites(uuid: String){
        _favorites.add(uuid)
        applyChange()
    }

    fun removeFromFavorites(uuid: String){
        _favorites.remove(uuid)
        applyChange()
    }

    private fun fetchFavorites(context: Context){
        context.getSharedPreferences(FAVORITES, MODE_PRIVATE).apply {
            getStringSet(FAVORITES, mutableSetOf())?.toMutableList()?.let {
                _favorites.addAll(it)
            }
        }
    }

    private fun applyChange(){
        editor.putStringSet(FAVORITES, _favorites.toMutableSet())
        editor.apply()
    }

    companion object {
        var instance : FavoritesSource? = null
        fun getInstance(context : Context) = synchronized(this) {
            instance ?: FavoritesSource().apply {
                fetchFavorites(context)
                editor = context.getSharedPreferences(FAVORITES, MODE_PRIVATE).edit()
            }.also {
                instance = it
            }
        }

        const val FAVORITES = "favorites"
    }
}

