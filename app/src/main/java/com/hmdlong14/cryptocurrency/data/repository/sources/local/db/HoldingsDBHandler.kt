package com.hmdlong14.cryptocurrency.data.repository.sources.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HoldingsDBHandler(private val context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // TODO implement later
    }

    companion object {
        const val DB_NAME = "cryptos"
        const val DB_VERSION = 1

        private const val TABLE_NAME = "holdings"
        private const val KEY_ID = "uuid"
        private const val KEY_AMOUNT = "amount"

        const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID VARCHAR PRIMARY KEY," +
                "$KEY_AMOUNT INT)"
        const val SQL_SELECT = "SELECT * FROM $TABLE_NAME"
    }
}