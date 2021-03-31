# BannerKit


## YdKit通用组件库
YdKit 是一组功能丰富的 Android 通用组件。

* [LogKit](https://github.com/ydstar/LogKit) — 轻量级的 Android 日志系统。
* [RestfulKit](https://github.com/ydstar/RestfulKit) — 简洁但不简单的 Android 网络组件库。
* [StorageKit](https://github.com/ydstar/StorageKit) — 高性能 Android 离线缓存框架。
* [ExecutorKit](https://github.com/ydstar/ExecutorKit) — 简洁易用的 Android 多线程操作框架。
* [CrashKit](https://github.com/ydstar/CrashKit) — 简洁易用的 Android Crash日志捕捉组件。
* [PermissionKit](https://github.com/ydstar/PermissionKit) — 简洁易用的 Android 权限请求组件。
* [NavigationBarKit](https://github.com/ydstar/NavigationBarKit) — 简洁易用的 Android 顶部导航条组件。
* [RefreshKit](https://github.com/ydstar/RefreshKit) — 简洁易用的 Android 下拉刷新和上拉加载组件。
* [AdapterKit](https://github.com/ydstar/AdapterKit) — 简洁易用的 Android 列表组件。
* [BannerKit](https://github.com/ydstar/BannerKit) — 简洁易用的 Android 无限轮播图组件。
* [TabBottomKit](https://github.com/ydstar/TabBottomKit) — 简洁易用的 Android 底部导航组件。


## 效果预览
<img src="https://github.com/ydstar/BannerKit/blob/main/preview/show.gif" alt="动图演示效果" width="250px">

轮播图组件

## 导入方式

仅支持`AndroidX`
```
dependencies {
     implementation 'com.android.ydkit:banner-kit:1.0.5'
}
```

## 使用方法

#### 1.在XML布局文件中添加IBanner
```java
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.banner.kit.banner.BannerKit
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        android:background="@android:color/darker_gray"
        app:autoPlay="false"
        app:layout_constraintTop_toTopOf="parent"
        app:loop="false"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 2.代码

定义bannerModel
```
public class Model extends BannerModel {
}

```

轮播图使用
```java
        val banner = findViewById<BannerKit>(R.id.banner)
        var indicator = ICircleIndicator(this)
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
```




## License
```text
Copyright [2021] [ydStar]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
