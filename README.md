# XBaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，
万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示，webview引擎，APP更新组件，APP崩溃日志组件（热更新组件暂未加入，有需要请联系）。
![BaseAndroid](https://github.com/fanhua1994/BaseAndroid/blob/master/image/logo.png?raw=true)


# 使用方式 初始化
> 建议使用compile project(path: ':baseandroidcore')导入项目。现将本项目下载，将baseandroidcore目录导入到as.即可。使用前请将BaseApplication加入项目  BaseActivity加入项目
## 功能介绍

### XBaseActivity
> 由于ButterKnife不能再lib中bind。所以必须继承XBaseActivity进行二次封装。
```
package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XbaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/10.
 */

public abstract class BaseActivity extends XbaseActivity {
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
CountDownUtil cd = new CountDownUtil(5000,1000);
cd.start(new CountDownUtil.setOnCountDownListener() {
	@Override
	public void onTick(int second) {

	}

	@Override
	public void onFinish() {
		ActivityStack.getInstance().clearAllActivity();
		kill();
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
boolean isok = FileUtil.getInstance().setContext(this).setIdCard(true).setFileType(FileUtil.CACHE_FILE).createWorkGroup("mycache12");
toast("执行结果：" + isok);

//其中setContext方法必须调用。setIdCard setFileType可以不调用。默认是自定义分组模式，外部储存有优先。
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
//set color for status bar
StatusBarCompat.setStatusBarColor(Activity activity, int color)
//add alpha to color
StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)

//translucent status bar
StatusBarCompat.translucentStatusBar(activity);

//should hide status bar background (default black background) when SDK >= 21
StatusBarCompat.translucentStatusBar(Activity activity, boolean hideStatusBarBackground);

setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,Toolbar toolbar, int statusColor)
 ```

 
 ### 网络请求
 > 本框架网络请求使用的是OkGo框架，文档地址如下[wiki](https://github.com/jeasonlzy/okhttp-OkGo/wiki)
 
 ### 杀死当前APP进程
 ```
 ActivityStack.getInstance().clearAllActivity();
kill();
```

### 表单验证
#### 创建自动校验User.java
```
public class User {
	
	@Check(name="用户年龄",method= Methods.IntSize,minvalue=10,maxvalue=100)
	private Integer age;
	
	@Check(name="用户性别",method=Methods.StringCheck,param = {"男","女"})
	private String sex;
	
	@Check(name="用户姓名",method=Methods.StringSize,maxlength = 4,minlength = 2)
	private String name;
	
	@Check(name="用户地址",method=Methods.StringSize,maxlength = 10,minlength = 2)
	private String address;
	
	@Check(name="用户邮箱",method=Methods.StringType,type= Validation.MAIL_VAIL)
	private String mail;
	
	@Check(name="用户身份证",method=Methods.StringRegex,regex="^\\d{17}(\\d|[A-Z])$")
	private String idcard;
	
	

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
```
#### 开始自动校验
```
User user = new User();
user.setAddress("四川");
user.setAge(90);
user.setName("董志平");
user.setSex("44444");
user.setMail("dddddd.com");
user.setIdcard("5141991013311X");

ValidMsg msg = Validation.AutoVerifiy(user);
//ValidMsg msg = Validation.StringSize(user.getName(), "用户姓名", 2, 10);//仅演示了校验长度，其他的方法请参考Method.java内部。

if(msg.isPass()){
    toast("验证通过");
}else{
   toast("验证失败："+msg.getMsg());
}
```
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
    android:authorities="com.hengyi.xbaseandroid.fileProvider"//这个请替换为你的软件包名
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
updateBean.setAuthority("com.hengyi.xbaseandroid.fileProvider");//兼容安卓7.0 安装  
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
```
