package com.example.android1

import android.content.Intent
import android.icu.text.IDNA.Info
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.android1.databinding.ActivityMainBinding
import com.example.ch11_jetpack.OneFragment
import com.example.ch11_jetpack.ThreeFragment
import com.example.ch11_jetpack.TwoFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    // 뷰 페이저 어댑터
    class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }

        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //ActionBarDrawerToggle 버튼 적용
        toggle = ActionBarDrawerToggle(
            this, binding.drawer, R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

//뷰 페이지에 어댑터 적용
        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

       /* //네비게이션 뷰 에서 아이템 선택 이벤트 추가
      //메뉴 자체에서 사용하는 게 아니라 뷰에서 NavigationView 이용해서 하는 것이기 때문에
      //override fun onOptionsItemSelected(item: MenuItem): Boolean(메뉴자체) 이걸로 하지 않는다.
        binding.naviMenu.setNavigationItemSelectedListener {
            if(it.itemId === R.id.item_info){
                val intent = Intent(this, InfoActivity::class.java)
                startActivity(intent)
            }
            true
        }*/

        //binding 안 쓰고 해보면 이렇게 됨 좀 더 편리하게 쓰려고 바인딩씀
       /* findViewById<NavigationView>(R.id.naviMenu).setNavigationItemSelectedListener {
            true
        }*/

        //내가 해보기
        //네비게이션 뷰 에서 아이템 선택 이벤트 추가
        binding.naviMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_info -> {
                    Log.d("myLog","info 메뉴 선택")
                    val intent = Intent(this, InfoActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_report -> {
                    val intent = Intent(this, ReportActivity::class.java)
                    Log.d("myLog","report 메뉴 선택")
                    startActivity(intent)
                }
                R.id.item_setting -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    Log.d("myLog","setting 메뉴 선택")
                    startActivity(intent)
                }
                R.id.item_notice -> {
                    val intent = Intent(this, NoticeActivity::class.java)
                    Log.d("myLog","notice 메뉴 선택")
                    startActivity(intent)
                }
                R.id.item_service_center -> {
                    val intent = Intent(this, ServiceActivity::class.java)
                    Log.d("myLog","service_center 메뉴 선택")
                    startActivity(intent)
                }
            }
            true
        }

    }

    //메뉴생성한 레이아웃을 구현
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        //MenuItem 객체를 얻고 그 안에 포함된 ActionView 객체 획득
        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                // 키보드의 검색 버튼을 클릭한 순간의 이벤트
                Log.d("myLog", "검색 키워드 : ${query}")
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                // 검색어 변경될 때마다 작동(실시간)이벤트
                return true
            }
        })
        return true
    }

    //메뉴생성한 레이아웃을 구현
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
// 이벤트가 토글 버튼에서 발생하면
        if (toggle.onOptionsItemSelected(item)) {
            Log.d("myLog", "토글버튼 클릭")
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
