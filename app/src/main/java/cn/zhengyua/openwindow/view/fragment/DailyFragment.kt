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
import cn.zhengyua.openwindow.viewmodel.DailyFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_daily.*

class DailyFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(DailyFragmentViewModel::class.java) }
    private lateinit var recAdapter: RecAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recLayoutManager = LinearLayoutManager(context)
        rv_daily.layoutManager = recLayoutManager
        recAdapter = context?.let { RecAdapter(it, viewModel.listData) }!!
        rv_daily.adapter = recAdapter
        viewModel.loadDaily()

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
                    viewModel.listData[position].description ?: "",
                    viewModel.listData[position].id,
                    viewModel.listData[position].blurred
                )
                bundle.putSerializable("data", topData)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onItemLongClick(view: View, position: Int) {

            }
        })

        viewModel.dailyPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                rv_daily.adapter?.notifyDataSetChanged()
                if (viewModel.listData[viewModel.listData.size - 1].nextUrl == null) {
                    refresh_layout_daily.setEnableLoadMore(false)
                }
            } else {
                val emptyRecData =
                    RecData("加载失败了,下拉刷新试试", "", "", 0, "", "", "", "", "", "", "textCard", "")
                viewModel.listData.clear()
                viewModel.listData.add(emptyRecData)
                rv_daily.adapter?.notifyDataSetChanged()
                refresh_layout_daily.setEnableLoadMore(false)
                Toast.makeText(context, "日报加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_daily.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_daily.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_daily.setOnRefreshListener {
            refresh_layout_daily.finishRefresh()
            viewModel.loadDaily()
            refresh_layout_daily.setEnableLoadMore(true)
        }

        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<RecData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                rv_daily.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_daily.finishLoadMoreWithNoMoreData()
                refresh_layout_daily.setEnableLoadMore(false)
            }

        })
        refresh_layout_daily.setOnLoadMoreListener {
            refresh_layout_daily.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        rv_daily.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recAdapter.isScrolling = false
                    recAdapter.notifyDataSetChanged()
                } else {
                    recAdapter.isScrolling = true
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}