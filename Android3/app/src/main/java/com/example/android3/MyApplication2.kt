package com.example.android3

import android.app.Application
import com.example.android3.retrofit.NetworkService2
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 레트로핏 초기화(어플이 구동될때 한번만 실행될 설정)
class MyApplication2 : Application() {
    companion object {
        val api_key = "bcFihF1ogYVJYtk2GlhfKr8XKiWxIGVnJlKHWdQR" // 회원가입을 통해 얻은 key값
        val BASE_URL = "https://api.nasa.gov" // 나사(NASA)api 제공 사이트

        val retrofit: Retrofit // 레트로핏 최초 설정
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //데이터 컨버팅을위한 세팅
                .build()
        var networdService2: NetworkService2

        init {
            networdService2 = retrofit.create(NetworkService2::class.java)//인터페이스에 네트워킹이 가능하도록 생성
        }
    }
}