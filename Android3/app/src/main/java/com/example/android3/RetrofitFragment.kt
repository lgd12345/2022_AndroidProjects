package com.example.android3

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android3.databinding.FragmentRetrofitBinding
import com.example.android3.model.PageListModel
import com.example.android3.recycler.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)
        var category:String? = this.arguments?.get("category").toString()
        if(category == "null"){
            category = null
        }
        val call: Call<PageListModel> = MyApplication.networdService.getList(
            MyApplication.API_KEY,
            "kr",
            category
        )
        //call 객체에 네트워킹 시도 명령
        call?.enqueue(object:Callback<PageListModel>{
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
//성공시 호출
                Log.d("myLog","성공 입니다. ${response.raw()}")
                if(response.isSuccessful){
                    binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.retrofitRecyclerView.adapter = MyAdapter(activity as Context, response.body()?.articles)
                }
            }
            override fun onFailure(call: Call<PageListModel>, t: Throwable) {
//실패시 호출
                Log.d("myLog","error 입니다.")
            }
        })
        return binding.root
    }
}