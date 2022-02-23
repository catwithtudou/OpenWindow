package cn.zhengyua.openwindow.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import cn.zhengyua.openwindow.R
import kotlinx.android.synthetic.main.activity_big_cover.*

class BigCoverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_cover)
        val url = intent.getStringExtra("url")
        Glide.with(this).load(url).into(img_big_cover)
    }
}
