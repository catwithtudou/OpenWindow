package cn.zhengyua.openwindow.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.zhengyua.openwindow.R
import cn.zhengyua.openwindow.bean.RecData
import cn.zhengyua.openwindow.bean.TopData
import cn.zhengyua.openwindow.view.activity.PlayVideoActivity
import cn.zhengyua.openwindow.view.adapter.RecAdapter
import cn.zhengyua.openwindow.viewmodel.RecFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_rec.*

class RecFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(RecFragmentViewModel::class.java) }
    private lateinit var recAdapter: RecAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recLayoutManager = LinearLayoutManager(context)
        rv_rec.layoutManager = recLayoutManager
        recAdapter = context?.let { RecAdapter(it,viewModel.listData) }!!
        rv_rec.adapter = recAdapter
        viewModel.loadRec()
        recAdapter.setOnItemClickListener(object : RecAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(context, PlayVideoActivity::class.java)
                val bundle = Bundle()
                val topData = TopData(
                    viewModel.listData[position].url,
                    viewModel.listData[position].playUrl,
                    viewModel.listData[position].time,
                    viewModel.listData[position].title,
                    viewModel.listData[position].author,
                    viewModel.listData[position].description?:"",
                    viewModel.listData[position].id,
                    viewModel.listData[position].blurred
                )
                bundle.putSerializable("data", topData)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            override fun onItemLongClick(view: View, position: Int) {}
        })

        viewModel.recPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                rv_rec.adapter?.notifyDataSetChanged()
                if (viewModel.listData[viewModel.listData.size - 1].nextUrl == null){
                    refresh_layout.setEnableLoadMore(false)
                }
            } else {
                val emptyRecData = RecData("加载失败了,下拉刷新试试","","",0,"","","","","","","textCard","")
                viewModel.listData.clear()
                viewModel.listData.add(emptyRecData)
                rv_rec.adapter?.notifyDataSetChanged()
                refresh_layout.setEnableLoadMore(false)
                Toast.makeText(context, "网络不太好哦，首页加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout.setRefreshHeader(ClassicsHeader(context))
        refresh_layout.setRefreshFooter(ClassicsFooter(context))
        refresh_layout.setOnRefreshListener {
            refresh_layout.finishRefresh()
            viewModel.loadRec()
            refresh_layout.setEnableLoadMore(true)
        }
        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<RecData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                rv_rec.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout.finishLoadMoreWithNoMoreData()
                refresh_layout.setEnableLoadMore(false)
            }

        })
        refresh_layout.setOnLoadMoreListener {
            refresh_layout.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        rv_rec.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    recAdapter.isScrolling = false
                    recAdapter.notifyDataSetChanged()
                } else
                {
                    recAdapter.isScrolling = true
                }
            }
        })
    }


}