![Jitpack](https://jitpack.io/v/fanhua1994/XBaseAndroid.svg)
![Platform](https://img.shields.io/badge/Platform-Android-ff69b4.svg)
![License](https://img.shields.io/github/license/alibaba/dubbo.svg)
![Author](https://img.shields.io/badge/Author-%E7%B9%81%E5%8D%8E-blue.svg)

# XBaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，
万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示，webview引擎，APP更新组件，APP崩溃日志组件，事件总线.


> 本人学疏才浅，非专业安卓开发，希望有兴趣的朋友一起加入进来完善框架，致力于打造最方便快捷的开发框架。由于最近我的全部业余时间都投入到了XBaseWeb-Plus的开发当中（基于Springboot的开发框架，让开发专注于业务功能，敏捷开发）。文档DEMO并非详细，大家可以看看源码，有建议或者不懂得地方欢迎打扰！（联系我的邮箱↓↓↓↓）


项目地址：[https://github.com/fanhua1994/XBaseAndroid](https://github.com/fanhua1994/XBaseAndroid)


新文档全部完善。

新文档地址：https://www.jianshu.com/nb/20489476

![BaseAndroid](https://github.com/fanhua1994/BaseAndroid/blob/master/image/logo.png?raw=true)

# 版本更新
目前最新版本是1.3.5
[更新日志](https://github.com/fanhua1994/XBaseAndroid/blob/master/LOG.md)

引用方式：

## 注意：

androidx请使用1.3.2及以上版本。不是androidx请用1.3.1以下版本

1.加入repositories
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2.引用第三方库
```
    implementation 'com.github.bumptech.glide:glide:4.10.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'

    api 'com.alibaba:fastjson:1.1.71.android'
    api 'com.lzy.net:okgo:3.0.4'
    api 'org.greenrobot:eventbus:3.1.1'
    api 'com.j256.ormlite:ormlite-core:5.1'
    api 'com.j256.ormlite:ormlite-android:5.1'
    api 'com.squareup.okhttp3:okhttp:4.2.2'
	//不使用webview不引用
    api 'com.just.agentweb:agentweb:4.1.2'
    api 'com.just.agentweb:filechooser:4.1.2'
    api 'com.download.library:Downloader:4.1.2'
```
3.引用XBaseAndroid
```
implementation 'com.github.fanhua1994:XBaseAndroid:1.3.5'
```

4.在gradle.properties文件下加入

> 1.3.2以上版本使用时，请添加以下代码

```
android.useAndroidX=true
android.enableJetifier=true
```

5.build.gradle新增代码
> android节点下添加

```
 compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

# 联系作者
简书：[http://www.jianshu.com/u/50c9e5f00da3](http://www.jianshu.com/u/50c9e5f00da3)

CSDN：[http://blog.csdn.net/dong_18383219470?viewmode=list](http://blog.csdn.net/dong_18383219470?viewmode=list)

# 代码提交


[点我立即提交代码](https://github.com/fanhua1994/XBaseAndroid/pulls)

