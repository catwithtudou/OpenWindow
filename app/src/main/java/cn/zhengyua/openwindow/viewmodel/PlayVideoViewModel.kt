package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.RecData
import cn.zhengyua.openwindow.bean.TopData
import cn.zhengyua.openwindow.repository.Repository

class PlayVideoViewModel : ViewModel() {

    val listData = mutableListOf<TopData>()

    private val relatedLiveData = MutableLiveData<String>()
    val relatedPathData = Transformations.switchMap(relatedLiveData) { query ->
        Repository.loadRelated(query)
    }

    val replyPathData = Transformations.switchMap(relatedLiveData) { query ->
        Repository.loadReply(query)
    }

    fun loadRelated(query: String) {
        relatedLiveData.value = query
    }
}