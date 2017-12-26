### 20171130（版本1.0.2）【已发布】
+ 解决了EventBus自动注册问题，取消自动注册。
+ 解决了LoadingDialogBUG，已经修复。
+ 解决了判断内存卡是否存在的BUG。


### 20171215（版本1.0.3）【已发布】
+ 优化Activity启动，改为从ActivityUtils启动。
+ 新增复制黏贴工具类ClipboardUtils。
+ 规范名称将Xbase改为XBase。
+ 将倒计时控件 改为单列模式，规范使用模式。
+ 整改校验框架，实现更全面的校验文本。

### 20171218（版本1.0.4）【未发布】
+ ActivityUtils.kill()会销毁全部Activity并杀死进程。不用再调用ActivityStack.getInstance().clearAllActivity();方法；
+ ActivityUtils新增Flag,Action参数；
+ 预计将引入下拉刷新上拉加载库；
+ 引入内存泄漏检测工具；
+ 移除蓝牙模块、移除校验模块，如需使用请单独引用。
+ 引入耗时操作线程池
+ 引入扫描二维码库
+ 引入图片浏览器
+ 添加列表选择弹窗
+ 优化网页浏览器