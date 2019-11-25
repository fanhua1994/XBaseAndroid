![Jitpack](https://jitpack.io/v/fanhua1994/XBaseAndroid.svg)
![Platform](https://img.shields.io/badge/Platform-Android-ff69b4.svg)
![License](https://img.shields.io/github/license/alibaba/dubbo.svg)
![Author](https://img.shields.io/badge/Author-%E7%B9%81%E5%8D%8E-blue.svg)

# XBaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，
万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示，webview引擎，APP更新组件，APP崩溃日志组件，事件总线.


项目地址：[https://github.com/fanhua1994/XBaseAndroid](https://github.com/fanhua1994/XBaseAndroid)


新文档全部完善。

新文档地址：https://www.jianshu.com/nb/20489476

![BaseAndroid](https://github.com/fanhua1994/BaseAndroid/blob/master/image/logo.png?raw=true)

# 版本更新
目前最新版本是1.3.2
[更新日志](https://github.com/fanhua1994/XBaseAndroid/blob/master/LOG.md)

引用方式：

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
api 'com.github.bumptech.glide:glide:4.8.0'
api 'jp.wasabeef:glide-transformations:4.0.0'
api 'com.google.code.gson:gson:2.8.5'
api 'com.lzy.net:okgo:3.0.4'
api 'org.greenrobot:eventbus:3.1.1'
api 'com.j256.ormlite:ormlite-core:5.1'
api 'com.j256.ormlite:ormlite-android:5.1'
api 'com.squareup.okhttp3:okhttp:4.2.2'
如果不使用webview相关功能，请不引用
api 'com.just.agentweb:agentweb:4.1.2'
api 'com.just.agentweb:filechooser:4.1.2'
api 'com.download.library:Downloader:4.1.2'
```
3.引用XBaseAndroid
```
implementation 'com.github.fanhua1994:XBaseAndroid:1.3.2'
```

4.在gradle.properties文件下加入

> 1.3.2以上版本使用时，请添加以下代码

```
android.useAndroidX=true
android.enableJetifier=true
```

# 联系作者
简书：[http://www.jianshu.com/u/50c9e5f00da3](http://www.jianshu.com/u/50c9e5f00da3)

CSDN：[http://blog.csdn.net/dong_18383219470?viewmode=list](http://blog.csdn.net/dong_18383219470?viewmode=list)

# 代码提交


[点我立即提交代码](https://github.com/fanhua1994/XBaseAndroid/pulls)

```
