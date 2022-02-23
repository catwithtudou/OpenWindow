package cn.zhengyua.openwindow.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import cn.zhengyua.openwindow.view.myview.MyRoundRectImageView
import com.to.aboomy.banner.HolderCreator


class ImageHolderCreator : HolderCreator {
    override fun createView(context: Context?, index: Int, o: Any?): View {
        val iv = MyRoundRectImageView(context)
        iv.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(iv).load(o).into(iv)
        iv.setOnClickListener { }
        return iv
    }
}