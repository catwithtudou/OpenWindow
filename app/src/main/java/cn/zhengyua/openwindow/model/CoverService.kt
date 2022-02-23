package cn.zhengyua.openwindow.model

import cn.zhengyua.openwindow.bean.WelcomeBean
import retrofit2.Call
import retrofit2.http.GET

interface CoverService {
    @GET("HPImageArchive.aspx?format=js&idx=0&n=1")
    fun getImageData(): Call<WelcomeBean>
}