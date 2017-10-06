# BaseAndroid
一款集成了网络请求，本地缓存，配置文件，数据库映射，权限申请，链表管理Activity，简化Activity、Service、Broadcast启动，万能ListView,GridView适配器、高仿IOS弹窗、倒计时/延迟执行，标题栏组件

## 功能介绍

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
BaseAndroid引入了easypermissions框架。使用如下
#### 1.检查权限
```
String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE};
if (EasyPermissions.hasPermissions(this, perms)) {
   //...     
} else {
    //...
}
```

#### 2.申请权限
```
EasyPermissions.requestPermissions(this, "拍照需要摄像头权限",
RC_CAMERA_AND_WIFI, perms);
```
#### 3.实现EasyPermissions.PermissionCallbacks接口，直接处理权限是否成功申请
```
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);

	// Forward results to EasyPermissions
	EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
}

//成功
@Override
public void onPermissionsGranted(int requestCode, List<String> list) {
	// Some permissions have been granted
	// ...
}

//失败
@Override
public void onPermissionsDenied(int requestCode, List<String> list) {
	// Some permissions have been denied
	// ...
}
```

### 5.创建工作组
> 工作组就好比我们将我们的外部储存分为很多很多目录，每个目录一个分组。文件互不影响。可以删除写入数据。目前默认的有db、cache、file分组。其他需要自定义。
```
boolean isok = FileUtil.getInstance().setContext(this).setIdCard(true).setFileType(FileUtil.CACHE_FILE).createWorkGroup("mycache12");
toast("执行结果：" + isok);

//其中setContext方法必须调用。setIdCard setFileType可以不调用。默认是自定义分组模式，外部储存有优先。
```
