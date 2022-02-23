package cn.zhengyua.openwindow.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ktx.immersionBar
import cn.zhengyua.openwindow.OpenWindowApplication.Companion.context
import cn.zhengyua.openwindow.R
import cn.zhengyua.openwindow.bean.AllTagData
import cn.zhengyua.openwindow.bean.SpecialData
import cn.zhengyua.openwindow.bean.TopData
import cn.zhengyua.openwindow.view.adapter.SpecialAdapter
import cn.zhengyua.openwindow.viewmodel.AllTagViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.activity_all_tag.*

class AllTagActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(AllTagViewModel::class.java) }
    private lateinit var allTagAdapter: SpecialAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tag)
        initBar()
        init()
    }

    private fun init() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        rv_all_tag.layoutManager = gridLayoutManager
        allTagAdapter = SpecialAdapter(this, viewModel.listData)
        rv_all_tag.adapter = allTagAdapter

        allTagAdapter.setOnItemClickListener(object : SpecialAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@AllTagActivity, TagActivity::class.java)
                intent.putExtra("id", viewModel.listData[position].id)
                startActivity(intent)
            }
            override fun onItemLongClick(view: View, position: Int) {}
        })

        viewModel.loadAllTag()
        viewModel.allTagPathData.observe(this, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                val tagList = mutableListOf<SpecialData>()
                for (i in 0 until list.size) {
                    val specialData = SpecialData(list[i].cover, list[i].title, list[i].id)
                    tagList.add(specialData)
                }
                viewModel.listData.clear()
                viewModel.listData.addAll(tagList)
                rv_all_tag.adapter?.notifyDataSetChanged()

            } else {
                val emptyAllTagData = SpecialData(
                    "",
                    "加载失败了,下拉刷新试试",
                    ""
                )
                viewModel.listData.clear()
                viewModel.listData.add(emptyAllTagData)
                rv_all_tag.adapter?.notifyDataSetChanged()
                refresh_layout_all_tag.setEnableLoadMore(false)
                Toast.makeText(context, "加载失败了", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_all_tag.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_all_tag.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_all_tag.setOnRefreshListener {
            refresh_layout_all_tag.finishRefresh()
            viewModel.loadAllTag()
            refresh_layout_all_tag.setEnableLoadMore(true)
        }
    }

    private fun initBar() {
        immersionBar {
            transparentStatusBar()  //透明状态栏，不写默认透明
            transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
            fullScreen(true)
            statusBarDarkFont(true)
            navigationBarDarkIcon(true)
            statusBarColor("#FBFBFB")
            navigationBarColor("#FBFBFB")
        }
        val params: WindowManager.LayoutParams = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }
}

