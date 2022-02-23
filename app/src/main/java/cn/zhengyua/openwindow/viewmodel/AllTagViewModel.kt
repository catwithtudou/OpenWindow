package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.bean.AllTagData
import cn.zhengyua.openwindow.bean.SpecialData
import cn.zhengyua.openwindow.repository.Repository

class AllTagViewModel:ViewModel() {
    val listData = mutableListOf<SpecialData>()
    private val allTagLiveData = MutableLiveData<Int>()
    val allTagPathData =
        Transformations.switchMap(allTagLiveData) { Repository.loadAllTag() }

    fun loadAllTag() {
        allTagLiveData.value = 0
    }
}