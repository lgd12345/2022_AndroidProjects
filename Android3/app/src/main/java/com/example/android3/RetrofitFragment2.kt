package com.example.android3

import android.content.ClipData.Item
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android3.databinding.Fragment2RetrofitBinding
import com.example.android3.model.ItemModel2
import com.example.android3.model.PageListModel2
import com.example.android3.recycler.MyAdapter2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = Fragment2RetrofitBinding.inflate(inflater, container, false)

        val call: Call<ItemModel2> = MyApplication2.networdService2.getList2(
            MyApplication2.api_key)
        //call 객체에 네트워킹 시도 명령
        call?.enqueue(object:Callback<ItemModel2>{
            override fun onResponse(call: Call<ItemModel2>, response: Response<ItemModel2>) {
//성공시 호출
                Log.d("myLog","성공 입니다. ${response.raw()}")
                Log.d("myLog","데이터 : ${response.body()}")
                if(response.isSuccessful){
                    var datas = mutableListOf<ItemModel2>()
                    var itemModel2 = ItemModel2(response.body()?.media_type ?:"null", response.body()?.date ?: "null", response.body()?.explanation ?: "null", response.body()?.title ?: "null", response.body()?.hdurl ?: "null", response.body()?.url ?: "null")
                    datas.add(itemModel2)
                    binding.retrofitRecyclerView2.layoutManager = LinearLayoutManager(activity)

                    binding.retrofitRecyclerView2.adapter = MyAdapter2(activity as Context, datas)
                }
            }
            override fun onFailure(call: Call<ItemModel2>, t: Throwable) {
//실패시 호출
                Log.d("mylog","error 입니다.")
            }
        })
        return binding.root
    }
}