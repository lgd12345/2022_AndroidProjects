package com.example.android2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var datas: MutableList<String>? = null
    var datas2: MutableList<String>? = null
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //AddActivity에서 intent에 putExtra해준거에서 result값을 받는다.
        //requestLauncher 생성
        val requestLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { //contract
                it.data?.getStringExtra("result")?.let {//callback
                    //let 사용해서 데이터 자체를 다룸 , 데이터가 절대 null이 아니라는 것을 명시하기 위해서 !!를 써준다.
                    //사용자가 값을 입력하고 넘어온 상태이기 때문이다.
                    //물음표 널 값허용

                    //그 전에 오류는 인덱스값과 데이터 갯수 값이 달라서 오류가 나는 거였다.
                    //날짜는 갱신이 안 된 채로 값이 불러와 지니까 인덱스값과 데이터값이 달라진다.
                    // 오늘에서 제일 값이 높은 데이터를 갱신시켜주는 내용을 입력해주고 갱신시켜서 앱이 정상작동 되게 한다.
                    datas?.add(it)
                    //DB에서 데이터를 가져온다.
                    val db = DBHelper(this).readableDatabase
                    val cursor = db.rawQuery("select max(today) from TODO_TB", null)
                    cursor.run {
                        while (moveToNext()){
                            datas2?.add(cursor.getString(0))
                        }
                    }
                    db.close()
                    adapter.notifyDataSetChanged()
                }

            }
        binding.mainFab.setOnClickListener {
//addActivity를 인텐트에 담아서 시스템에 전달
            val intent = Intent(this, AddActivity::class.java)
            requestLauncher.launch(intent)
        }
        datas = savedInstanceState?.let {
            //번들객체에 데이터가 있을 때
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            // it.getStringArrayList("datas")?.toMutableList()
            //        } 이게 널이면 mutableListOf<String>()를 실행시킨다. 엘비스연산자(?:)는 왼쪽이 null이면 null을 반환
            //번들객체에 데이터가 null일 때
            mutableListOf<String>()

        }
        datas2 = savedInstanceState?.let {
            //번들객체에 데이터가 있을 때
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            // it.getStringArrayList("datas")?.toMutableList()
            //        } 이게 널이면 mutableListOf<String>()를 실행시킨다. 엘비스연산자(?:)는 왼쪽이 null이면 null을 반환
            //번들객체에 데이터가 null일 때
            mutableListOf<String>()

        }
        //DB에서 데이터를 가져온다.
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from TODO_TB", null)
        cursor.run {
            while (moveToNext()){
                Log.d("myLog","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@${cursor.getString(2)}")
                datas?.add(cursor.getString(1))
                datas2?.add(cursor.getString(2))
                Log.d("myLog","@@@@@@@@@@@@@@@@@@@@@@@${datas2}")
                Log.d("myLog","@@@@@@@@@@@@@@@@@@@@@@@${datas}")
            }
        }
        db.close()

        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager = layoutManager
        adapter = MyAdapter(datas,datas2)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))

    }
}