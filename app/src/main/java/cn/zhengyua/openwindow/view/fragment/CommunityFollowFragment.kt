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
import cn.zhengyua.openwindow.bean.CommunityFollowData
import cn.zhengyua.openwindow.bean.TopData
import cn.zhengyua.openwindow.view.activity.PlayVideoActivity
import cn.zhengyua.openwindow.view.adapter.AutoPlayAdapter
import cn.zhengyua.openwindow.viewmodel.CommunityFollowFragmentViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.fragment_community_follow.*

class CommunityFollowFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(CommunityFollowFragmentViewModel::class.java) }
    private lateinit var followAdapter: AutoPlayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_community_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recLayoutManager = LinearLayoutManager(context)
        rv_community_follow.layoutManager = recLayoutManager
        followAdapter = context?.let { AutoPlayAdapter(it, viewModel.listData) }!!
        rv_community_follow.adapter = followAdapter
        followAdapter.setOnItemClickListener(object : AutoPlayAdapter.OnItemClickListener {
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

            override fun onItemLongClick(view: View, position: Int) {}
        })
        viewModel.loadFollow()
        viewModel.communityFollowPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                rv_community_follow.adapter?.notifyDataSetChanged()

            } else {
                val emptyRecData =
                    CommunityFollowData(
                        "",
                        "",
                        0,
                        0,
                        "",
                        "加载失败了,下拉刷新试试",
                        "",
                        "",
                        "",
                        "textCard",
                        0,
                        0,
                        0,
                        "",
                        ""
                    )
                viewModel.listData.clear()
                viewModel.listData.add(emptyRecData)
                rv_community_follow.adapter?.notifyDataSetChanged()
                refresh_layout_community_follow.setEnableLoadMore(false)
                Toast.makeText(context, "日报加载失败了>_<", Toast.LENGTH_SHORT).show()
            }
        })
        refresh_layout_community_follow.setRefreshHeader(ClassicsHeader(context))
        refresh_layout_community_follow.setRefreshFooter(ClassicsFooter(context))
        refresh_layout_community_follow.setOnRefreshListener {
            refresh_layout_community_follow.finishRefresh()
            viewModel.loadFollow()
            refresh_layout_community_follow.setEnableLoadMore(true)
        }

        viewModel.morePathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            val emptyData = mutableListOf<CommunityFollowData>()
            if (list != null && list != emptyData) {
                viewModel.listData.addAll(list)
                rv_community_follow.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "已经是最后一个啦", Toast.LENGTH_SHORT).show()
                refresh_layout_community_follow.finishLoadMoreWithNoMoreData()
                refresh_layout_community_follow.setEnableLoadMore(false)
            }

        })
        refresh_layout_community_follow.setOnLoadMoreListener {
            refresh_layout_community_follow.finishLoadMore()
            viewModel.loadMore(viewModel.listData[viewModel.listData.size - 1].nextUrl)
        }

        rv_community_follow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    followAdapter.isScrolling = false
                    followAdapter.notifyDataSetChanged()
                } else {
                    followAdapter.isScrolling = true
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}