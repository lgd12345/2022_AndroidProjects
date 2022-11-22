package com.example.android3.retrofit

import com.example.android3.model.PageListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    //네트워킹을 위한 함수 작성
    @GET("/v2/top-headlines")
    fun getList(
        @Query("apiKey") apiKey: String?,
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<PageListModel> //결과를 PageListModel에 담는다.
}