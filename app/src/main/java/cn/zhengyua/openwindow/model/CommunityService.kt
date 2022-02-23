package cn.zhengyua.openwindow.model


import cn.zhengyua.openwindow.bean.CommunityFollowBean
import cn.zhengyua.openwindow.bean.CommunityRecBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface CommunityService {
    @GET("v7/community/tab/rec")
    fun getCommunity(): Call<CommunityRecBean>

    @GET
    fun getMoreCommunityRec(@Url url: String): Call<CommunityRecBean>

    @GET("v6/community/tab/follow")
    fun getCommunityFollow(): Call<CommunityFollowBean>
}