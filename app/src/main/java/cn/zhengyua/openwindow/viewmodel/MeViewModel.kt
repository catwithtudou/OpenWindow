package cn.zhengyua.openwindow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.zhengyua.openwindow.repository.Repository

class MeViewModel:ViewModel() {
    var isHind = false
    private val imageLiveData = MutableLiveData<Int>()
    val imagePathData = Transformations.switchMap(imageLiveData) { Repository.loadImage() }
    fun loadImage() {
        imageLiveData.value = 0
    }
}