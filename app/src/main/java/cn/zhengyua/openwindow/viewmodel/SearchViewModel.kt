package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.RecData
import cn.zhengyua.openwindow.repository.Repository


class SearchViewModel : ViewModel() {
    val listData = mutableListOf<RecData>()
    private val searchLiveData = MutableLiveData<String>()
    val searchPathData =
        Transformations.switchMap(searchLiveData) { query -> Repository.loadSearch(query) }

    fun loadSearch(query: String) {
        searchLiveData.value = query
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData = Transformations.switchMap(moreLiveData) { url -> Repository.loadMore(url) }
    fun loadMore(url: String?) {
        moreLiveData.value = url
    }
}