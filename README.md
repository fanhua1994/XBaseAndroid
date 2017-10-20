# BaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，
万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示，webview引擎，APP更新组件，APP崩溃日志组件（热更新组件暂未加入，有需要请联系）。
![BaseAndroid](https://github.com/fanhua1994/BaseAndroid/blob/master/image/logo.png?raw=true)


# 使用方式 初始化
> 建议使用compile project(path: ':baseandroidcore')导入项目。现将本项目下载，将baseandroidcore目录导入到as.即可。使用前请将BaseApplication加入项目  BaseActivity加入项目
## 功能介绍

### BaseActivity
> 由于ButterKnife不能再lib中不能bind。所以必须继承BaseActivity进行二次封装。
```
package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/10.
 */

public abstract class MyBaseActivity extends BaseActivity {
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

### 权限设置
> permissionUtils查看使用方法

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
> WebEngineActivity.java需要在Mainfast.xml注册activity
```
StartActivity(WebEngineActivity.class,new String[]{"url"},"https://yunqi.aliyun.com/?open_id=5a5a2d8b-e185-4efa-8722-4a841b72c7f4--1199333720&open_cid=3483#/video/detail1106");
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

#### 先添加Student表
```
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

### 感谢以下项目的支持
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