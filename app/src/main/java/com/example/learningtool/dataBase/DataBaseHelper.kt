package com.example.learningtool.dataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASENAME = "CLOCK_TIME_DATABASE"
val TABLENAME = "time_record"
val COL_TIME = "time"
val COL_ID = "id"
class DataBaseHandler(var context: Context?) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLENAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_TIME TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLENAME)
        onCreate(db)
    }

    fun insertData(item: Item) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TIME, item.time)
        val result = database.insert(TABLENAME, null, contentValues)
        database.close()
    }

    fun clearData() {
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS " + TABLENAME)
        onCreate(db)
    }

    fun deleteItem(row : Int) {
        val db = this.writableDatabase
        db.delete(TABLENAME, COL_ID + "=" + (row + 1), null)
    }

    fun getLast() : Item? {
        val db = this.readableDatabase
        val query = ("SELECT * FROM $TABLENAME ORDER BY $COL_ID DESC LIMIT 1")
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            val item = Item(result.getLong(result.getColumnIndex(COL_TIME)))
            return item
        }
        return null
    }

    fun updateItem(key : Int, item : Item) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TIME, item.time)
        db.update(TABLENAME, contentValues, COL_ID + "=" + key, null)
    }

    fun isEmpty() : Boolean {
        val db = this.readableDatabase
        val query = "select count(*) from $TABLENAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            db.close()
            return false
        }
        db.close()
        return true
    }

    fun readData(): MutableList<Item> {
        val list: MutableList<Item> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val item = Item()
                item.time = result.getLong(result.getColumnIndex(COL_TIME))
                list.add(item)
            }
            while (result.moveToNext())
        }
        db.close()
        return list
    }
}