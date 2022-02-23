package cn.zhengyua.openwindow.model

import cn.zhengyua.openwindow.bean.RecBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("v3/search")
    fun getSearch(@Query("query") query: String): Call<RecBean>
}