package cn.zhengyua.openwindow.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cn.zhengyua.openwindow.R
import cn.zhengyua.openwindow.bean.ColumnData
import cn.zhengyua.openwindow.bean.SpecialData
import cn.zhengyua.openwindow.view.myview.MyRoundRectImageView
import kotlinx.android.synthetic.main.item_column.view.*
import kotlinx.android.synthetic.main.item_special.view.*

class ColumnAdapter(val context: Context, private val mList: List<ColumnData>?) :
    RecyclerView.Adapter<ColumnAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: MyRoundRectImageView = view.img_column_item
        val title: TextView = view.tv_title_column_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_column, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mList == null)
            return 2
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList != null) {
            Glide.with(context).load(mList[position].url).into(holder.image)
            holder.title.text = mList[position].title
        } else  holder.title.text = "加载失败了"
    }

}