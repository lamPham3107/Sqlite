package com.example.sqlite_test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val bt_add = findViewById<Button>(R.id.bt_add)
        val bt_del = findViewById<Button>(R.id.bt_del)
        val bt_show = findViewById<Button>(R.id.bt_show)
        val bt_edit = findViewById<Button>(R.id.bt_edit)
        val txtResult = findViewById<TextView>(R.id.txt_result)

        bt_add.setOnClickListener {

            val user = username.text.toString()
            val pass = password.text.toString()
            val userList = databaseHelper.getAllUsers()
            if(userList.isNotEmpty()){
                databaseHelper.deleteUser(userList[0].first)
            }
            databaseHelper.insertUser(user, pass)
        }
        bt_edit.setOnClickListener {
            val newUser = username.text.toString()
            val newPass = password.text.toString()
            val userList = databaseHelper.getAllUsers()

            if (userList.isNotEmpty()) {
                val oldUser = userList[0].first
                val success = databaseHelper.updateUser(oldUser, newUser, newPass)
                if (success) {
                    println("Cập nhật thành công!")
                } else {
                    println("Cập nhật thất bại!")
                }
            } else {
                println("Không có user nào để cập nhật!")
            }
        }
        bt_del.setOnClickListener {
            val user = username.text.toString()
            val success = databaseHelper.deleteUser(user)
            if (success) {
                println("Xóa thành công!")
            } else {
                println("Không tìm thấy tài khoản để xóa!")
            }
            username.text.clear()
            password.text.clear()
        }
        bt_show.setOnClickListener {
            val userList = databaseHelper.getAllUsers()
            val result = userList.joinToString("\n") { "Tên: ${it.first}\nMật khẩu: ${it.second}" }
            txtResult.text = result
        }
    }
}