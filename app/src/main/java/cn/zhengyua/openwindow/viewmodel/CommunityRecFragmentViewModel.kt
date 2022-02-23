package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.CommunityRecData
import cn.zhengyua.openwindow.repository.Repository

class CommunityRecFragmentViewModel : ViewModel() {
    val listData = mutableListOf<CommunityRecData>()
    private val communityLiveData = MutableLiveData<Int>()
    val communityPathData =
        Transformations.switchMap(communityLiveData) { Repository.loadCommunity() }

    fun loadCommunity() {
        communityLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreCommunityRec(url) }
    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}