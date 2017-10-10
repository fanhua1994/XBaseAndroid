# BaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示，webview引擎，APP更新组件。
![BaseAndroid](https://github.com/fanhua1994/BaseAndroid/blob/master/image/logo.png?raw=true)


# 使用方式
> 建议使用compile project(path: ':baseandroidcore')导入项目。现将本项目下载，将baseandroidcore目录导入到as.即可。使用前请将BaseApplication加入项目  BaseActivity加入项目
## 功能介绍

### BaseActivity
> 由于BaseActivity已经进行简单的封装，所以不方便进行修改。因此方式如下
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
```
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