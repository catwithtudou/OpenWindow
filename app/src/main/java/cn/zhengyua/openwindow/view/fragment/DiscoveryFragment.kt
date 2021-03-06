package cn.zhengyua.openwindow.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import cn.zhengyua.openwindow.R
import cn.zhengyua.openwindow.view.activity.AllTagActivity
import cn.zhengyua.openwindow.view.activity.PlayVideoActivity
import cn.zhengyua.openwindow.view.activity.TagActivity
import cn.zhengyua.openwindow.view.adapter.*
import cn.zhengyua.openwindow.viewmodel.DiscoveryFragmentViewModel
import com.to.aboomy.banner.IndicatorView
import com.to.aboomy.banner.ScaleInTransformer

import kotlinx.android.synthetic.main.fragment_discovery.*


class DiscoveryFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(DiscoveryFragmentViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBanner()
        loadSpecial()
        loadColumn()
        loadTop()
        loadTopic()
    }

    private fun loadTop() {
        val topLayoutManager = LinearLayoutManager(context)
        rv_discovery_list.layoutManager = topLayoutManager
        viewModel.topPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                val topAdapter = context?.let { TopAdapter(it, list) }
                rv_discovery_list.adapter = topAdapter
                topAdapter?.setOnItemClickListener(object : TopAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, PlayVideoActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("data", list[position])
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
            } else {
                Toast.makeText(context, "?????????????????????>_<", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadColumn() {
        val columnLayoutManager = GridLayoutManager(context, 2)
        columnLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        rv_discovery_column.layoutManager = columnLayoutManager
        viewModel.columnPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                val columnAdapter = context?.let { ColumnAdapter(it, list) }
                rv_discovery_column.adapter = columnAdapter
            } else {
                Toast.makeText(context, "?????????????????????>_<", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadSpecial() {
        val specialLayoutManager = GridLayoutManager(context, 2)
        specialLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        rv_discovery_special.layoutManager = specialLayoutManager
        viewModel.specialPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.listData.clear()
                viewModel.listData.addAll(list)
                val specialAdapter = context?.let { SpecialAdapter(it, list) }
                specialAdapter?.setOnItemClickListener(object :
                    SpecialAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(context, TagActivity::class.java)
                        intent.putExtra("id", viewModel.listData[position].id)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                    }
                })
                rv_discovery_special.adapter = specialAdapter
            }
        })
        tv_special_all.setOnClickListener {
            val intent = Intent(context,AllTagActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadBanner() {
        val qyIndicator = IndicatorView(context)
            .setIndicatorColor(Color.DKGRAY)
            .setIndicatorSelectorColor(Color.WHITE)
        viewModel.loadBanner()
        viewModel.bannerPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list != null) {
                banner.setIndicator(qyIndicator)
                    .setHolderCreator(ImageHolderCreator())
                    .setAutoTurningTime(5000)
                    .setPageMargin(20, 15)
                    .setPageTransformer(true, ScaleInTransformer())
                    .setPages(list)
            } else {
                Toast.makeText(context, "banner???????????????>_<", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadTopic(){
        val topicLayoutManager = LinearLayoutManager(context)
        rv_discovery_topic.layoutManager = topicLayoutManager
        viewModel.topicPathData.observe(viewLifecycleOwner, Observer { result ->
            val list = result.getOrNull()
            if (list !== null) {
                val topicAdapter = context?.let { TopicAdapter(it, list) }
                rv_discovery_topic.adapter = topicAdapter
            } else {
                Toast.makeText(context, "?????????????????????>_<", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}