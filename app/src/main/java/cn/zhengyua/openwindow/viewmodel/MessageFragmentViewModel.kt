package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.CommunityFollowData
import cn.zhengyua.openwindow.bean.CommunityRecData
import cn.zhengyua.openwindow.bean.MessageData
import cn.zhengyua.openwindow.repository.Repository

class MessageFragmentViewModel : ViewModel() {
    val listData = mutableListOf<MessageData>()
    private val messageLiveData = MutableLiveData<Int>()
    val messagePathData =
        Transformations.switchMap(messageLiveData) { Repository.loadMessage() }

    fun loadMessage() {
        messageLiveData.value = 0
    }

    private val moreLiveData = MutableLiveData<String>()
    val morePathData =
        Transformations.switchMap(moreLiveData) { url -> Repository.loadMoreMessage(url) }

    fun loadMore(url: String) {
        moreLiveData.value = url
    }
}