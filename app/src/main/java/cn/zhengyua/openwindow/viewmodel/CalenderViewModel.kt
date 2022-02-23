package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.CommunityRecData
import cn.zhengyua.openwindow.repository.Repository

class CalenderViewModel : ViewModel() {
    val listData = mutableListOf<CommunityRecData>()
    private val calenderLiveData = MutableLiveData<String>()
    val calenderPathData =
        Transformations.switchMap(calenderLiveData) { date -> Repository.loadCalender(date) }

    fun loadCalender(date: String) {
        calenderLiveData.value = date
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData =
        Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreCommunityRec(url) }

    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}