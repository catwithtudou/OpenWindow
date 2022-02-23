package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.RecData
import cn.zhengyua.openwindow.repository.Repository

class DailyFragmentViewModel : ViewModel() {
    var listData = mutableListOf<RecData>()
    private val dailyLiveData = MutableLiveData<Int>()
    val dailyPathData = Transformations.switchMap(dailyLiveData) { Repository.loadDaily() }
    fun loadDaily() {
        dailyLiveData.value = 0
    }
    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) {url-> Repository.loadMoreDaily(url) }
    fun loadMore(url:String?) {
        moreLiveData.value = url
    }
}