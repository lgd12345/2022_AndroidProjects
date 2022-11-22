package com.example.android3.retrofit

import com.example.android3.model.ItemModel2
import com.example.android3.model.PageListModel2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService2 {
    //네트워킹을 위한 함수 작성
    @GET("/planetary/apod")
    fun getList2(
        @Query("api_key") api_key: String?
    ): Call<ItemModel2> //결과를 PageListModel에 담는다.

    //동영상
    @GET("/planetary/apod")
    fun getList3(
        @Query("api_key") api_key: String,
        @Query("thumbs") thumbs: String?,
    ):Call<PageListModel2>
}