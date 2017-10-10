# BaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件,图片显示

# 使用方式
> 建议使用compile project(path: ':baseandroidcore')导入项目。现将本项目下载，将baseandroidcore目录导入到as.即可。使用前请将BaseApplication加入项目  BaseActivity加入项目
## 功能介绍

### 0.BaseActivity
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

### 1.弹窗
![](https://github.com/fanhua1994/BaseAndroid/blob/master/image/%E4%BB%BFIOS%E5%BC%B9%E5%87%BA%E6%8F%90%E7%A4%BA%E6%A1%86.png?raw=true)
```
CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
dialog.setTitle("温馨提示");
dialog.setMsg("你好啊");
dialog.show();
```

### 2.可以选择的弹窗
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

### 3.倒计时控件
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

### 4.权限设置
```
// 在Activity：
AndPermission.with(activity)
    .requestCode(100)
    .permission(Permission.SMS)
    .rationale(...)
    .callback(...)
    .start();

// 在Fragment：
AndPermission.with(fragment)
    .requestCode(101)
    .permission(
        // 申请多个权限组方式：
        Permission.LOCATION,
        Permissioin.STORAGE
    )
    .rationale(...)
    .callback(...)
    .start();

// 在其它任何地方：
AndPermission.with(context)
    .requestCode(102)
    .permission(Permission.LOCATION)
    .rationale(...)
    .callback(...)
    .start();

// 如果你不想申请权限组，仅仅想申请某一个权限：
AndPermission.with(this)
    .requestCode(300)
    .permission(Manifest.permission.WRITE_CONTACTS)
    .rationale(...)
    .callback(...)
    .start();

// 如果你不想申请权限组，仅仅想申请某几个权限：
AndPermission.with(this)
    .requestCode(300)
    .permission(
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_SMS
    )
    .rationale(...)
    .callback(...)
    .start();
```

```
private PermissionListener listener = new PermissionListener() {
    @Override
    public void onSucceed(int requestCode, List<String> grantedPermissions) {
        // 权限申请成功回调。
        
        // 这里的requestCode就是申请时设置的requestCode。
        // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
        if(requestCode == 200) {
            // TODO ...
        }
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        // 权限申请失败回调。
        if(requestCode == 200) {
            // TODO ...
        }
    }
};
```

### 5.创建工作组
> 工作组就好比我们将我们的外部储存分为很多很多目录，每个目录一个分组。文件互不影响。可以删除写入数据。目前默认的有db、cache、file分组。其他需要自定义。
```
boolean isok = FileUtil.getInstance().setContext(this).setIdCard(true).setFileType(FileUtil.CACHE_FILE).createWorkGroup("mycache12");
toast("执行结果：" + isok);

//其中setContext方法必须调用。setIdCard setFileType可以不调用。默认是自定义分组模式，外部储存有优先。
```
