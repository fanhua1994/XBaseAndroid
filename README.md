# BaseAndroid
一款快速开发的基本框架

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
