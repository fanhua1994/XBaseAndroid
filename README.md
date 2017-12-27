# XBaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，
万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示，webview引擎，APP更新组件，APP崩溃日志组件，事件总线（热更新组件暂未加入，有需要请联系）。

项目地址：[https://github.com/fanhua1994/XBaseAndroid](https://github.com/fanhua1994/XBaseAndroid)

![BaseAndroid](https://github.com/fanhua1994/BaseAndroid/blob/master/image/logo.png?raw=true)

# 版本更新
目前最新版本是1.0.3
[更新日志](https://github.com/fanhua1994/XBaseAndroid/blob/master/LOG.md)

# 联系作者
简书：[http://www.jianshu.com/u/50c9e5f00da3](http://www.jianshu.com/u/50c9e5f00da3)

CSDN：[http://blog.csdn.net/dong_18383219470?viewmode=list](http://blog.csdn.net/dong_18383219470?viewmode=list)

# 代码提交
> 需要提交代码的朋友请邮箱联系并附上代码即可：90fanhua@gmail.com

# 引入方式
在项目root gradle加入
```
allprojects {
    repositories {
        google()
        jcenter()
	//加入以下仓库
        maven { url 'https://jitpack.io' }
    }
}
```
app下的gradle加入
```
dependencies {
	compile 'com.github.fanhua1994:XBaseAndroid:1.0.3'
}
```

# 初始化框架
## 1.初始化Application
```
<application
android:name="com.hengyi.baseandroidcore.base.XBaseApplication"
...
...
/>
```

## 2.继承XBaseActivity实现ButterKnife视图自动注入功能，不使用可不继承，直接使用XBaseActivity
> 由于ButterKnife不能再lib中bind。所以必须继承XBaseActivity进行二次封装。XBaseAndroid不提供视图注解，如需使用请自行引入以下库。
```
compile 'com.jakewharton:butterknife:8.5.1'
annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'
```
```
package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/10.
 */

public abstract class BaseActivity extends XBaseActivity {
    private Unbinder unbind = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbind = ButterKnife.bind(this);
    }

    @Override
    public int setBaseContentView() {
        return setContentView();
    }

    public abstract int setContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}

```
## 功能介绍
### 弹窗
![](https://github.com/fanhua1994/BaseAndroid/blob/master/image/%E4%BB%BFIOS%E5%BC%B9%E5%87%BA%E6%8F%90%E7%A4%BA%E6%A1%86.png?raw=true)
```
CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
dialog.setTitle("温馨提示");
dialog.setMsg("你好啊");
dialog.show();
```

### 可以选择的弹窗
![](https://github.com/fanhua1994/BaseAndroid/blob/master/image/%E4%BB%BFIOS%E5%BC%B9%E5%87%BA%E9%80%89%E6%8B%A9%E6%A1%86.png?raw=true)
```
CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
dialog.setTitle("温馨提示");
dialog.setMsg("你好啊");
dialog.setNegativeButton("确定", new View.OnClickListener() {
	@Override
	public void onClick(View view) {
		toast("点击了确定");
	}
});

dialog.setPositiveButton("取消",null);
dialog.show();
```

### 弹出输入框
![](https://github.com/fanhua1994/BaseAndroid/blob/master/image/dialog_confirm.png?raw=true)
```
CustomConfirmDialog dialog = new CustomConfirmDialog(this).builder();
dialog.setTitle("温馨提示");
dialog.setInputNumber(false);
dialog.setNegativeButton("取消", new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});

dialog.setPositiveButton("取消",new CustomConfirmDialog.OnPostListener(){

    @Override
    public void OnPost(String value) {
	toast(value);
    }
});
dialog.show();
```

### 加载Loading
![](https://github.com/fanhua1994/BaseAndroid/blob/master/image/dialog_loading.png?raw=true)
```
showLoadingDialog("正在加载");
closeLoadingDialog();
```

### 倒计时控件
```
countdownUtils = CountdownUtils.getInstance();
countdownUtils.start(100000,1000);
countdownUtils.setCountdownListener(new CountdownUtils.setOnCountDownListener() {
    @Override
    public void onTick(int second) {
	numberProgressBar.setProgress(100 - second);
    }

    @Override
    public void onFinish() {

    }
});
```

### 定时器控件
```
TimerUtils timer = TimerUtils.getInstance();
timer.setTimer_id(123);
timer.startTimer(5);//单位秒
timer.setTimerListener(new TimerUtils.TimerTaskCallbackListener() {
    @Override
    public void Timing(int timer_id, int timer_count) {
	toast("timer_id:"+timer_id);
    }
});
```

### 权限申请
AndroidManifest.xml
```
<uses-permission android:name="android.permission.READ_CONTACTS"></uses-permission>
```
```
PermissionUtils.requestPermissions(this,200,new String[]{"android.permission.READ_CONTACTS"},new PermissionUtils.OnPermissionListener(){

    @Override
    public void onPermissionGranted() {
        toast("权限申请成功");
    }

    @Override
    public void onPermissionDenied(String[] deniedPermissions) {
        toast("权限申请失败");
    }
});
```

### 创建工作组
> 工作组就好比我们将我们的外部储存分为很多很多目录，每个目录一个分组。文件互不影响。可以删除写入数据。目前默认的有db、cache、file分组。其他需要自定义。
```
boolean isok = ProjectUtils.getInstance().setIdCard(true).setFileType(FileUtil.CACHE_FILE).createWorkGroup("mycache12");
toast("执行结果：" + isok);

//setIdCard setFileType可以不调用。默认是自定义分组模式，外部储存有优先。
```

### 本地缓存使用
```
DiskLruCacheHelper cache = DiskLruCacheHelper.getInstance(this);
cache.put("cache","21424984034324934320434940494044904239");
toast(cache.getAsString("cache") +"缓存大小："+cache.getCacheCount());
```

### 配置文件使用
> ConfigUtil自主维护了一个哈希表可以很快的根据数据缓存拿到数据，不用去xml里面读取，性能很不错。
```
ConfigUtils config = ConfigUtils.getInstance(this);
config.addOrUpdateText("name","繁华");
toast(config.findStringByKey("name"));
config.clearConfig();
```

### 标题栏使用
```
<com.hengyi.baseandroidcore.weight.EaseTitleBar
 xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
app:titleBarTitle="标题栏演示"
app:titleBarLeftImage="@drawable/icon_back"
android:layout_height="50dp">
</com.hengyi.baseandroidcore.weight.EaseTitleBar>

```
```
@BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View view) {
	ActivityStack.getInstance().popActivity();
    }
});
```

### 如何高效关闭Activity
```
ActivityStack.getInstance().popActivity();
千万不要使用finish();
```

### 调用web引擎
> WebEngineActivity.java需要在Mainfast.xml注册activity。最新修复web引擎支持隐藏标题栏。
```
 StartActivity(WebEngineActivity.class,new String[]{WebEngineActivity.WEB_URL_PARAM,WebEngineActivity.WEB_SHOW_TITLE_BAR},"http://www.baidu.com/",false);
```

### 本地数据库使用
#### 创建实体Student
```
package com.hengyi.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/10/8.
 */

@DatabaseTable
public class Student {

    @DatabaseField
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DatabaseField
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
#### 创建StudentDao
```
package com.hengyi.db;

import android.content.Context;

import com.hengyi.baseandroidcore.database.BaseDao;
import com.hengyi.baseandroidcore.database.BaseDaoImpl;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/10/11.
 */

public class StudentDao {
    private BaseDao<Student,Integer> studentDao;

    public StudentDao(Context context){
        studentDao = new BaseDaoImpl<>(context,Student.class);
    }

    public int add(Student s){
        try {
            return studentDao.save(s);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
```

#### 初始化数据库（切记在启动界面做好初始化，或者Application文件做好相关初始化操作）
```
DatabaseHelper.setDatabase("easy",2);//设置数据库版本及名称
DatabaseHelper.addTable(Student.class);
```
#### 使用
```
StudentDao sd = new StudentDao(context);
Student s = new Student();
s.setId(1);
s.setName("ggeegegerger");
int res = sd.add(s);
toast("添加学生结果是：" + res);
```

#### 数据库版本变化监听
```
DatabaseHelper.getInstance(this).setDatabaseVersionChangeListener(new DatabaseVersionChangeListener() {
    @Override
    public void onChange(int oldVersion, int newVersion) {
	toast("数据库版本繁盛变化：老版本:"+oldVersion +" 新版本："+newVersion);
	if(newVersion == 2){
	    try {
		DatabaseHelper.getInstance(getContext()).getDao(Student.class).executeRaw("ALTER TABLE 'student' ADD COLUMN sex int");
		toast("数据更新成功");
	    } catch (SQLException e) {
		e.printStackTrace();
		toast("数据更新失败");
	    }
	}
    }
});

toast("当前数据库版本："+DatabaseHelper.getInstance(this).getVersion() +"数据库名："+DatabaseHelper.getInstance(this).getDatabaseName());
```

### 提供状态栏工具
```
StatusBarCompat.setStatusBarColor(Activity activity, int color)
StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)
StatusBarCompat.translucentStatusBar(activity);
//需要隐藏状态栏背景需要SDK版本大于21
StatusBarCompat.translucentStatusBar(Activity activity, boolean hideStatusBarBackground);
setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,Toolbar toolbar, int statusColor)
 ```

 ### 网络请求
 > 本框架网络请求使用的是OkGo框架，文档地址如下[网络请求文档猛戳查看](https://github.com/jeasonlzy/okhttp-OkGo/wiki)
 
 ### 杀死当前APP进程
 ```
 ActivityStack.getInstance().clearAllActivity();
kill();
```

### 表单校验
> 表单校验作为重要的模块，我独立出来了，需要使用的请看详解介绍咯。
[参考文档](https://github.com/fanhua1994/java_validation)


> 如果您只需要接入校验模块，请参考这个地址：[https://github.com/fanhua1994/java_validation](https://github.com/fanhua1994/java_validation)

### APP更新组件（AppUpdateManager）
![](https://github.com/fanhua1994/BaseAndroid/blob/master/image/APP_update.png?raw=true)
+ 支持MD5文件校验
+ 支持强制更新(isForce)
+ 支持在获取root情况下静默安装
+ 支持下载完跳转到安装界面
+ 支持安卓7.0
+ 支持各类回调接口

### 如何兼容Android7.0
#### AndroidMainfest.xml
application节点下添加
```
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="com.hengyi.XBaseandroid.fileProvider"//这个请替换为你的软件包名
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths"/>
</provider>
```
#### 将demo res-> xml文件夹复制到你的应用目录。

```
UpdateBean updateBean = new UpdateBean();
updateBean.setDescription("今日更新了XBaseAndroid框架的更新管理器。");
updateBean.setDownload_url("http://file.cleveriip.com:88/group2/M00/00/03/rBJbXVnlcPCATMAtAtnNwW8wwRs625.apk");
updateBean.setForce(true);//是否强制更新 或 静默安装
updateBean.setAuthority("com.hengyi.XBaseandroid.fileProvider");//兼容安卓7.0 安装  
updateBean.setMd5_code("a034366c2257281060a3ee27df38a793");
updateBean.setNew_version("1.0.0.1");

updateBean.setTitle("新版本来啦，立即更新吧");
AppUpdateManager appUpdateManager = AppUpdateManager.getInstance();
appUpdateManager.checkUpdate(updateBean,this);
appUpdateManager.setAppUpdateListener(new AppUpdateManager.AppUpdateListener() {


    @Override
    public void downloadProgressBar(String progress, String speed) {
	Log.d("AppUpdateManager","进度条：" + progress +"   下载速度："  + speed);
    }

    @Override
    public void downloadSuccess(File app_path) {
	Log.d("AppUpdateManager","下载成功    路径如下：" + app_path.getAbsolutePath());
    }

    @Override
    public void downloadStart() {
	Log.d("AppUpdateManager","下载开始");
    }

    @Override
    public void downloadError(String message) {
	Log.d("AppUpdateManager","下载错误");
    }

    @Override
    public void downloadFinish() {
	Log.d("AppUpdateManager","下载结束");
    }

    @Override
    public void cancelDownload() {
	Log.d("AppUpdateManager","取消下载");
    }

    @Override
    public void NoUpdate() {
	Log.d("AppUpdateManager","没有更新");
    }
});
```

### 下载日志
```
:48:39.332 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：78.49   下载速度：789.25kb/s
11-12 05:48:39.645 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：79.43   下载速度：889.85kb/s
11-12 05:48:39.945 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：80.80   下载速度：1.03mb/s
11-12 05:48:40.250 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：82.20   下载速度：1.18mb/s
11-12 05:48:40.548 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：83.84   下载速度：1.37mb/s
11-12 05:48:41.186 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：85.09   下载速度：1.40mb/s
11-12 05:48:41.488 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：85.91   下载速度：1.45mb/s
11-12 05:48:41.791 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：86.79   下载速度：1.49mb/s
11-12 05:48:42.089 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：88.55   下载速度：1.66mb/s
11-12 05:48:42.440 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：89.74   下载速度：1.70mb/s
11-12 05:48:42.740 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：90.63   下载速度：1.71mb/s
11-12 05:48:43.046 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：91.54   下载速度：1.71mb/s
11-12 05:48:43.358 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：92.31   下载速度：1.61mb/s
11-12 05:48:43.663 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：93.06   下载速度：1.51mb/s
11-12 05:48:43.974 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：93.96   下载速度：1.40mb/s
11-12 05:48:44.309 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：94.55   下载速度：1.39mb/s
11-12 05:48:44.641 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：95.88   下载速度：1.44mb/s
11-12 05:48:45.027 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：96.70   下载速度：1.41mb/s
11-12 05:48:45.336 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：97.53   下载速度：1.27mb/s
11-12 05:48:45.638 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：98.34   下载速度：1.23mb/s
11-12 05:48:45.937 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：99.18   下载速度：1.23mb/s
11-12 05:48:46.243 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：99.93   下载速度：1.20mb/s
11-12 05:48:46.250 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 进度条：100.00   下载速度：1.35mb/s
11-12 05:48:46.253 9028-9028/com.hengyi.baseandroiddemo D/AppUpdateManager: 下载成功    路径如下：/storage/emulated/0/Android/data/com.hengyi.baseandroiddemo/download/XBaseAndroid_软件更新_1.0.0.1.apk
```

### 图片压缩
#### 效果与对比

内容 | 原图 | `Luban` | `Wechat`
---- | ---- | ------ | ------
截屏 720P |720*1280,390k|720*1280,87k|720*1280,56k
截屏 1080P|1080*1920,2.21M|1080*1920,104k|1080*1920,112k
拍照 13M(4:3)|3096*4128,3.12M|1548*2064,141k|1548*2064,147k
拍照 9.6M(16:9)|4128*2322,4.64M|1032*581,97k|1032*581,74k
滚动截屏|1080*6433,1.56M|1080*6433,351k|1080*6433,482k

# 使用

### 异步调用

`Luban`内部采用`IO`线程进行图片压缩，外部调用只需设置好结果监听即可：

```
Luban.with(this)
        .load(photos)                                   // 传人要压缩的图片列表
        .ignoreBy(100)                                  // 忽略不压缩图片的大小
        .setTargetDir(getPath())                        // 设置压缩后文件存储位置
        .setCompressListener(new OnCompressListener() { //设置回调
          @Override
          public void onStart() {
            // TODO 压缩开始前调用，可以在方法内启动 loading UI
          }

          @Override
          public void onSuccess(File file) {
            // TODO 压缩成功后调用，返回压缩后的图片文件
          }

          @Override
          public void onError(Throwable e) {
            // TODO 当压缩过程出现问题时调用
          }
        }).launch();    //启动压缩
```

### 同步调用

同步方法请尽量避免在主线程调用以免阻塞主线程，下面以rxJava调用为例

```
Flowable.just(photos)
    .observeOn(Schedulers.io())
    .map(new Function<List<String>, List<File>>() {
      @Override public List<File> apply(@NonNull List<String> list) throws Exception {
        // 同步方法直接返回压缩后的文件
        return Luban.with(MainActivity.this).load(list).get();
      }
    })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe();
```

### 通知栏工具类
```
NotifacationUtils notifacation = new NotifacationUtils(this);//实例化通知栏
notifacation.createProgressNotify(R.drawable.ic_launcher,200,"正在下载中","APP更新","App正在准备下载",new Intent());//创建进度条通知栏
notifacation.showProgressNotify(progress2,"当前下载网速" + speed);//设置进度条及文字显示

//notifacation.createNotify();//创建普通通知栏
```

### 事件总线
```
//使用时，注册就行了 
onCreate(){
	EventBus.getDefault().register(this);
}

//界面销毁时 反注册
onDestroy(){
	EventBus.getDefault().unregister(this);
}
```
```
@Subscribe(threadMode = ThreadMode.MAIN)  
public void onMessageEvent(DefaultMessageEvent event) {/* Do something */};
```
如何发送消息？
```
EventManager.sendDefaultMessage(DefaultMessageEvent defaultMessageEvent);
```
DefaultMessageEvent是默认的消息类，您可以自定义消息。但默认的支持扩展数据，完全足够使用。DefaultMessageEvent支持以下的数据格式。
```
private Object obj;//传输对象
private int code;//传输数字
private int status;
private int mint;
private int type;
private float mfloat;//传输单精度数字
private double mdouble;//传输双精度数字
private long mlong;//传输长整型
private String content;//传输字符串
private Map<String,Object> map;//扩展包
```

### 数字进度条
![](https://github.com/fanhua1994/XBaseAndroid/blob/master/image/%E6%95%B0%E7%BB%84%E8%BF%9B%E5%BA%A6%E6%9D%A1.gif?raw=true)
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >

    <com.hengyi.baseandroidcore.weight.NumberProgressBar
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/numberbar"
        android:layout_width="wrap_content"
        android:padding="20dp"
        app:progress_current="0"
        style="@style/NumberProgressBar_Default"
        android:layout_height="wrap_content" />

</LinearLayout>

```
模拟进度条 采用倒计时控件
```
CountDownUtils countDownUtils = new CountDownUtils(100000,1000);
countDownUtils.start(new CountDownUtils.setOnCountDownListener() {
    @Override
    public void onTick(int second) {
	numberProgressBar.setProgress(100 - second);
    }

    @Override
    public void onFinish() {

    }
});
```

### 复制黏贴（不再兼容API 11）
```
ClipboardUtils.getInstance().copy(Context context,String text);//复制
ClipboardUtils.getInstance().paste(Context context)//黏贴
ClipboardUtils.getInstance().setListener(Context context,ClipboardManager.OnPrimaryClipChangedListener m_listener);
```

### 网络监测
```
boolean status = NetworkUtils.isNetworkConnected(Context context);
```

### JSON字符串转对象
源码如下：
```
public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
	if(gson == null)
	    gson = new Gson();
	T result = gson.fromJson(jsonData, type);
	return result;
}
```
使用如下：
```
User users = GsonUtils.parseJsonWithGson("{\"id\":1,\"name\":\"董志平\"}",User.class);
```

### ListView、GridView万能适配器
我们编写一个Adapter
```
package com.zhiweism.youerplatformparent.adapter;

import android.content.Context;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhiweism.youerplatformparent.R;
import com.zhiweism.youerplatformparent.table.FriendApply;
import com.zhiweism.youerplatformparent.table.FriendApplyDao;
import com.zhiweism.youerplatformparent.utils.GeneralUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */
public class FriendApplyAdapter extends CommonAdapter<FriendApply> {
    private Context context;

    public FriendApplyAdapter(Context context, List<FriendApply> data, int layout_id) {
        super(context, data, layout_id);
        this.context = context;
    }

    @Override
    public void ViewHolder(CommonViewHolder holder, int position) {
        final FriendApply friendApply = getItem(position);
        holder.setText(R.id.tv_username,friendApply.getUsername(),null);
        holder.setText(R.id.tv_message,friendApply.getMessage(),null);

        holder.setViewListener(R.id.refuse,new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    EMClient.getInstance().contactManager().declineInvitation(friendApply.getUsername());
                    data.remove(friendApply);
                    FriendApplyDao.getInstance().remove(friendApply);
                    notifyDataSetChanged();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.setViewListener(R.id.pass,new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(friendApply.getUsername());
                    data.remove(friendApply);
                    FriendApplyDao.getInstance().remove(friendApply);
                    notifyDataSetChanged();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

```
#### 相关方法详解，CommonViewHolder。

#### 可想而知，支持事件回调
```
void setText(int layout_id,String text,View.OnClickListener listener);
```

#### 设置图片
```
void setImage(int layout_id,String image_url,View.OnClickListener listener)
```
#### 设置原图
```
 void setCircleImage(int layout_id,String image_url,View.OnClickListener listener)
 ```

#### 获取一个控件视图
```
View getView(int layout_id)
```

#### 获取任意控件，泛型返回
```
<T> T getView(int layout_id,Class<T> type)
```

#### 控件设置监听单击事件
```
void setViewListener(int layout_id,View.OnClickListener listener)
```

### 执行耗时任务，线程池使用
我这里只讲如何的使用线程池，详细请到demo中查看。下面创建100个线程。但是同时只会创建4个新的对象。当线程池空时，去队列拿线程，直到创建的线程数量 + 1 = maxPoolSize就会停止创建。
```
//创建线程池  实际中执行的线程是比maxPoolSize少一个的。
HandlerExecutorPool handlerExecutorPool = HandlerExecutorPool.getInstance(5,200);
for(int i = 0;i < 100;i++){
    LoginThread loginThread = new LoginThread();
    Thread thread = new Thread(loginThread);
    handlerExecutorPool.execute(thread);
}
```
//关闭线程池
```
handlerExecutorPool.stutdown();
```

### 如何设置APP主题色
修改colors.xml
```
<color name="main_color">#20a7e6</color>
```

### 如何让状态栏颜色与主题色相同（沉浸式标题栏）
```
StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,R.color.main_color)));
```

### 万能适配器使用方法
```
package com.hengyi.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.hengyi.baseandroidcore.adapter.CommonAdapter;
import com.hengyi.baseandroidcore.adapter.CommonViewHolder;
import com.hengyi.baseandroiddemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class BluetoothsAdapter extends CommonAdapter<BluetoothDevice> {

    public BluetoothsAdapter(Context context, List<BluetoothDevice> data, int layout_id) {
        super(context, data, layout_id);
    }

    private String getStatus(int status){
        if(status == BluetoothDevice.BOND_BONDED){
            return "已配对";
        }else if(status == BluetoothDevice.BOND_BONDING){
            return "配对中";
        }else{
            return "未配对";
        }
    }

    @Override
    public void ViewHolder(CommonViewHolder holder, int position) {
        BluetoothDevice bluetoothDevice = getItem(position);
        holder.setText(R.id.name,bluetoothDevice.getName(),null);
        holder.setText(R.id.address,bluetoothDevice.getAddress(),null);
        holder.setText(R.id.status,getStatus(bluetoothDevice.getBondState()),null);
    }
}
```

![](https://github.com/fanhua1994/XBaseAndroid/blob/master/image/EB0196D85B484AF56FE41943CF9F757A.png?raw=true)
### 感谢以下开源项目的支持
```
compile 'com.google.code.gson:gson:2.6.2'
compile 'com.j256.ormlite:ormlite-core:4.48'
compile 'com.j256.ormlite:ormlite-android:4.48'
compile 'com.squareup.okhttp3:okhttp:3.9.0'
compile 'com.github.bumptech.glide:glide:3.7.0'
compile 'jp.wasabeef:glide-transformations:2.0.2'
compile 'com.github.johnkil.android-appmsg:appmsg:1.2.0'
compile 'com.lzy.net:okgo:3.0.4'
compile 'top.zibin:Luban:1.1.3'
https://github.com/daimajia/NumberProgressBar
```
