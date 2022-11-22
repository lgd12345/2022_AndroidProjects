package com.example.android3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.android3.databinding.ActivityMainBinding
import kotlinx.coroutines.awaitAll

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var retrofitFragment: RetrofitFragment
    lateinit var retrofitFragment2: RetrofitFragment2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofitFragment2 = RetrofitFragment2()
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_content, retrofitFragment2)
            .commit()
        supportActionBar?.title = "Retrofit Test"
        // 버튼 메인화면으로 이동
        binding.mainFab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_nasa) {
            retrofitFragment2 = RetrofitFragment2()
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_content, retrofitFragment2)
                .commit()
            supportActionBar?.title = "우주 사진"
        } else if (item.itemId === R.id.menu_all) {
            val bundle: Bundle = Bundle()
            bundle.putString("category", "null")
            retrofitFragment = RetrofitFragment()
            retrofitFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, retrofitFragment)
                .commit()
            supportActionBar?.title = "전체 뉴스"
        } else if (item.itemId === R.id.menu_health) {
            val bundle: Bundle = Bundle()
            bundle.putString("category", "health")
            retrofitFragment = RetrofitFragment()
            retrofitFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, retrofitFragment)
                .commit()
            supportActionBar?.title = "건강 뉴스"
        }
        return super.onOptionsItemSelected(item)
    }
}