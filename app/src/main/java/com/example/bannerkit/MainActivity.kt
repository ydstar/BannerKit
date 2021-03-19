package com.example.bannerkit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.banner.kit.banner.IBanner
import com.banner.kit.core.adapter.IBannerModel
import com.banner.kit.indicator.ICircleIndicator
import com.banner.kit.indicator.IIndicator
import com.banner.kit.indicator.INumIndicator
import com.bumptech.glide.Glide
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var mUrlList = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    private var mIndicator: IIndicator<*>? = null
    private var mAutoPlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView(ICircleIndicator(this), false)

        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener { _, isChecked ->
            mAutoPlay = isChecked
            initView(mIndicator, mAutoPlay)
        }

        findViewById<View>(R.id.tv_switch).setOnClickListener {
            //mHiBanner.setAutoPlay(false)
            if (mIndicator is ICircleIndicator) {
                initView(INumIndicator(this), mAutoPlay)
            } else {
                initView(ICircleIndicator(this), mAutoPlay)
            }
        }
    }

    private fun initView(hiIndicator: IIndicator<*>?, autoPlay: Boolean) {
        this.mIndicator = hiIndicator
        val mBanner = findViewById<IBanner>(R.id.banner)
        val moList: MutableList<IBannerModel> = ArrayList()
        for (i in 0..6) {
            val mo = BannerModel()
            mo.url = mUrlList[i]
            moList.add(mo)
        }
        mBanner!!.setIndicator(hiIndicator)
        mBanner.setAutoPlay(autoPlay)
        mBanner.setIntervalTime(2000)
        mBanner.setLoop(true)

        //自定义布局
        mBanner.setBannerData(R.layout.banner_item_layout, moList)

        //绑定数据
        mBanner.setBindAdapter { viewHolder, mo, position ->
            val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
            Glide.with(this@MainActivity).load(mo.url).into(imageView)
            val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url
            Log.d("----position:", position.toString() + "url:" + mo.url)
        }

    }
}