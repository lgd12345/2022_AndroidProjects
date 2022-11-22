package com.example.android3.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android3.R
import com.example.android3.databinding.Item2MainBinding
import com.example.android3.model.ItemModel2

class MyViewHolder2(val binding: Item2MainBinding) :
    RecyclerView.ViewHolder(binding.root)

class MyAdapter2(val context: Context, val datas: MutableList<ItemModel2>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder =
        MyViewHolder2(
            Item2MainBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder2).binding
        val model = datas!![position]
        binding.itemTitle.text = model.title
        binding.itemDesc.text = model.date
        binding.itemTime.text = "${model.media_type}"

//이미지는 glide라이브러리를 활용
        Glide.with(context)
            .load(model.url) //서버로부터 받은 url이미지의 문자열
            .override(300,300)//크기조절
            .placeholder(R.mipmap.ic_launcher_round)//이미지 로딩을 시작하기전에 보여줄 이미지
            .error(R.drawable.a404)
            .into(binding.itemImage) // 받은 이미지를 itemImage에 출력
    }
}