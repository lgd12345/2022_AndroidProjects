package com.example.android2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.android2.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //할일 등록 화면에서 저장버튼 클릭시 실행
        binding.nbtn.setOnClickListener {
//할일 등록 화면에서 저장버튼 클릭시 실행
            val inputData = binding.addEditView.text.toString()
            val intent = intent
            var todoText = binding.addEditView.text.toString()

            if (todoText.isBlank()) {
//할일 입력값이 없을떄
                //RESULT_CANCELED 작업 취소
                //비워진채로 intent 넘어감
                Toast.makeText(this.getApplicationContext(), "문자를 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
//할일 입력값이 있을떄
                //RESULT_OK 작업 성공
                val db = DBHelper(this).writableDatabase
                db.execSQL(
                    "insert into TODO_TB (todo) values (?)",
                    arrayOf<String>(inputData)
                )
                db.close()
                intent.putExtra("result", todoText)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
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