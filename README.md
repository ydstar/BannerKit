# BannerKit

<img src="https://github.com/ydstar/BannerKit/blob/main/preview/show.gif" alt="动图演示效果" width="250px">

轮播图组件

## 导入方式

仅支持`AndroidX`
```
dependencies {
     implementation 'com.android.ydkit:banner-kit:1.0.0'
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

    <com.banner.kit.banner.IBanner
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
public class BannerModel extends IBannerModel {
}

```

轮播图使用
```java
        val banner = findViewById<IBanner>(R.id.banner)
        var indicator = ICircleIndicator(this)
        val moList: MutableList<IBannerModel> = ArrayList()
        for (i in 0..mUrlList.size -1) {
            val mo = BannerModel()
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
