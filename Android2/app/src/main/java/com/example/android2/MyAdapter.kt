package com.example.android2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas: MutableList<String>?, val datas2: MutableList<String>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var db: DBHelper
    lateinit var context: Context

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        context = parent.context
        db = DBHelper(context)

        return MyViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas!![position]
        binding.itemData2.text = datas2!![position]

        //수정
        binding.itemData.setOnClickListener{
            Log.d("myLog","아이템 데이터 클릭")
            if (binding.updateBtn.visibility == View.GONE){
                Log.d("myLog","업데이트 버튼 표시")
                binding.updateBtn.visibility = View.VISIBLE
                binding.updateBtn.setOnClickListener {
                    val intent = Intent(binding.itemData.context, UpdateActivity::class.java)
                    intent.putExtra("todo",binding.itemData.text.toString())
                    startActivity(context,intent,null)
                }
            }else{
                binding.updateBtn.visibility = View.GONE
            }
        }

        // 삭제
        binding.deleteBtn.setOnClickListener {
            try{
                val data = binding.itemData.text.toString()
                val deldb = db.writableDatabase
                deldb.execSQL("delete from TODO_TB where todo = ?", arrayOf(data))
                deldb.close()

                //삭제후 화면 갱신
                datas.remove(datas!![position])
                notifyDataSetChanged()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}
