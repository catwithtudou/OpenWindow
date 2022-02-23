package cn.zhengyua.openwindow.model

import cn.zhengyua.openwindow.bean.AllTagBean
import retrofit2.Call
import retrofit2.http.GET

interface AllTagService {
    @GET("v4/categories/all")
    fun getAllTag():Call<AllTagBean>
}