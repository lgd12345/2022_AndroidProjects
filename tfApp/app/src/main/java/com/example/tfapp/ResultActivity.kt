package com.example.tfapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.io.IOException
import java.util.*

class ResultActivity : AppCompatActivity() {
//     안에 뷰가 별로 없을 때는 이렇게(lateinit var) 직접 쓰고 출력이 많으면 바인딩으로 쓴다.
//     ClassifierWithModel이거 도움으로 분류작업
    lateinit var cls: ClassifierWithModel
    lateinit var morebtn: Button
    lateinit var resultimg: ImageView
    lateinit var resulttxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
//// init쓰는 이유는 초기화
        cls = ClassifierWithModel(this)
        try {
            cls.init()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        morebtn = findViewById(R.id.restart_btn)
        resultimg = findViewById(R.id.result_img)
        resulttxt = findViewById(R.id.result_txt)

        val byteArray = intent.getByteArrayExtra("image")
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

// 분류는 여기서 일어난다. (resultStr) 여기는 결과 가져옴 
        //first(위치이름),second(퍼센트) 둘다 받음
        if (bitmap != null) {
            val (first,second) = cls.classify(bitmap)
            val resultStr: String =
                java.lang.String.format(Locale.ENGLISH, "%s", first)
            resultimg.setImageBitmap(bitmap)
            resulttxt.setText(resultStr+"\n"+second)

        }


        morebtn.setOnClickListener {
            val intent = Intent(applicationContext, InputActivity::class.java)
            finish()
            cls.finish()
            startActivity(intent)
        }

    }
}