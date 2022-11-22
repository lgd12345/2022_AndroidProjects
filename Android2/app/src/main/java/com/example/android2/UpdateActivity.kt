package com.example.android2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android2.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    lateinit var todo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        todo = intent.getStringExtra("todo").toString()
        binding.updateEditView.setText(todo)

        //할일 등록 화면에서 저장버튼 클릭시 실행
        binding.nbtn.setOnClickListener {
            val inputData = binding.updateEditView.text.toString()
            //할일 문자열이 비어져 있을 때
            if (inputData.isBlank()) {
                Toast.makeText(this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                false
            } else {
                //할일이 있을 때
                val db = DBHelper(this).writableDatabase
                db.execSQL(
                    "update TODO_TB set todo = ? where todo = ?", arrayOf(inputData, todo)
                )
                db.close()

                val intent = Intent(this, MainActivity::class.java)
                /*intent.putExtra("result", inputData)*/
                startActivity(intent)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //add............................
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
//할일 등록 화면에서 취소버튼 클릭시 실행
            R.id.menu_add_save -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}