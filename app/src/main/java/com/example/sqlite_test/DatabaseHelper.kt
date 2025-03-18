package com.example.sqlite_test

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "account.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "account"
        const val COLUMN_username = "username"
        const val COLUMN_password = "password"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_username TEXT, $COLUMN_password TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertUser(username: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_username, username)
            put(COLUMN_password, password)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }
    fun updateUser(oldUsername: String, newUsername: String, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_username, newUsername)
            put(COLUMN_password, newPassword)
        }
        val result = db.update(TABLE_NAME, values, "$COLUMN_username=?", arrayOf(oldUsername))
        db.close()
        return result > 0
    }

    // Lấy toàn bộ dữ liệu
    fun getAllUsers(): List<Pair<String, String>> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val userList = mutableListOf<Pair<String, String>>()

        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val phone = cursor.getString(1)
            userList.add(Pair(name, phone))
        }

        cursor.close()
        db.close()
        return userList
    }

    // Xóa người dùng theo tên
    fun deleteUser(name: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_username=?", arrayOf(name))
        db.close()
        return result > 0
    }
}