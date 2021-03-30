package com.example.bannerkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.banner.kit.banner.BannerKit

import com.banner.kit.core.adapter.BannerModel

import com.banner.kit.indicator.*
import com.bumptech.glide.Glide
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var mUrlList = arrayOf(
        "http://pic.618mt.com/d/file/bigpic/2021/03/18/0iwxicj1y4u.jpg",
        "http://pic.618mt.com/d/file/bigpic/2021/03/18/w3yqazfxfgu.jpg",
        "http://pic.618mt.com/d/file/bigpic/2021/03/18/vyluj1n4bap.jpg",
        "http://pic.618mt.com/d/file/bigpic/2021/03/18/ql1lao3dquv.jpg"
    )

    private var mIndicator: BannerIndicator<*>? = null
    private var mAutoPlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView(CircleIndicator(this), false)

        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener { _, isChecked ->
            mAutoPlay = isChecked
            initView(mIndicator, mAutoPlay)
        }

        findViewById<View>(R.id.tv_switch).setOnClickListener {
            if (mIndicator is CircleIndicator) {
                initView(NumIndicator(this), mAutoPlay)
            } else {
                initView(CircleIndicator(this), mAutoPlay)
            }
        }
    }

    private fun initView(indicator: BannerIndicator<*>?, autoPlay: Boolean) {
        this.mIndicator = indicator
        val banner = findViewById<BannerKit>(R.id.banner)
        val moList: MutableList<BannerModel> = ArrayList()
        for (i in 0..mUrlList.size -1) {
            val mo = Model()
            mo.url = mUrlList[i]
            moList.add(mo)
        }
        banner!!.setIndicator(indicator)
        banner.setAutoPlay(autoPlay)
        banner.setIntervalTime(2000)
        banner.setLoop(true)

        //自定义布局
        banner.setBannerData(R.layout.banner_item_layout, moList)

        //绑定数据
        banner.setBindAdapter { viewHolder, mo, position ->
            val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
            Glide.with(this@MainActivity).load(mo.url).into(imageView)
            val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url
            Log.d("----position:", position.toString() + "url:" + mo.url)
        }

    }
}