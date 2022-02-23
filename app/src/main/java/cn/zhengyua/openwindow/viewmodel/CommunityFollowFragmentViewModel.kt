package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.CommunityFollowData
import cn.zhengyua.openwindow.bean.CommunityRecData
import cn.zhengyua.openwindow.repository.Repository

class CommunityFollowFragmentViewModel:ViewModel() {
    val listData = mutableListOf<CommunityFollowData>()
    private val communityFollowLiveData = MutableLiveData<Int>()
    val communityFollowPathData =
        Transformations.switchMap(communityFollowLiveData) { Repository.loadCommunityFollow() }

    fun loadFollow() {
        communityFollowLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreFollow(url) }
    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}