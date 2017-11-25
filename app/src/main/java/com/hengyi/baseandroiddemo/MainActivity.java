package com.hengyi.baseandroiddemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hengyi.baseandroidcore.app.AppUpdateManager;
import com.hengyi.baseandroidcore.app.UpdateBean;
import com.hengyi.baseandroidcore.base.XbaseWebActivity;
import com.hengyi.baseandroidcore.database.DatabaseHelper;
import com.hengyi.baseandroidcore.database.DatabaseVersionChangeListener;
import com.hengyi.baseandroidcore.dialog.CustomConfirmDialog;
import com.hengyi.baseandroidcore.utils.CountDownUtils;
import com.hengyi.baseandroidcore.utils.NotifacationUtils;
import com.hengyi.baseandroidcore.utilscode.PermissionUtils;
import com.hengyi.baseandroidcore.validation.ValidMsg;
import com.hengyi.baseandroidcore.validation.Validation;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;
import com.hengyi.db.Student;
import com.hengyi.db.StudentDao;
import com.hengyi.event.MessageEvent;
import com.hengyi.validation.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomConfirmDialog confirmDialog = new CustomConfirmDialog(this).builder();
        confirmDialog.setTitle("请输入您的姓名");
        confirmDialog.setInputNumber(false);//限制数字
        confirmDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        confirmDialog.setPositiveButton("确定",new CustomConfirmDialog.OnPostListener(){
            @Override
            public void OnPost(String value) {
                toast(value);
            }
        });
        confirmDialog.show();



        showLoadingDialog("正在加载");

        CountDownUtils countDownUtils = new CountDownUtils(10000,1000);
        countDownUtils.start(new CountDownUtils.setOnCountDownListener() {
            @Override
            public void onTick(int second) {

            }

            @Override
            public void onFinish() {
                closeLoadingDialog();
            }
        });



        //扫描表
        DatabaseHelper.addTable(Student.class);
        DatabaseHelper.setDatabase("easy",2);

        DatabaseHelper.getInstance(this).setDatabaseVersionChangeListener(new DatabaseVersionChangeListener() {
            @Override
            public void onChange(int oldVersion, int newVersion) {
                toast("数据库版本发生变化：老版本:"+oldVersion +" 新版本："+newVersion);
                if(oldVersion < 2){
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

        studentDao = new StudentDao(this);
        toast("学生数量：" + studentDao.count());
        List<Student> allstudent = studentDao.getAll();
        if(allstudent == null){
            toast("数据为空");
        }else{
            toast(allstudent.get(0).getName());
        }

        easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setAddress("四川");
                user.setAge(90);
                user.setName("董志平");
                user.setSex("44444");
                user.setMail("dddddd@163.com");
                user.setIdcard("514199101331343241X");

                ValidMsg msg = Validation.AutoVerifiy(user);
                //ValidMsg msg = Validation.StringSize(user.getName(), "用户姓名", 2, 10);//仅演示了校验长度，其他的方法请参考Method.java内部。

                if(msg.isPass()){
                    toast("验证通过");
                }else{
                   toast("验证失败："+msg.getMsg());
                }

            }
        });
     }

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.cache_admin,R.id.web,R.id.database,R.id.requestPermission,R.id.checkUpdate})
    public void Click(View view){
        switch(view.getId()){
            case R.id.cache_admin:
                StartActivity(CacheActivity.class);
                break;

            case R.id.web:
                StartActivity(XbaseWebActivity.class,new String[]{XbaseWebActivity.WEB_URL_PARAM, XbaseWebActivity.WEB_SHOW_TITLE_BAR},"file:///android_asset/index.html",false);
                break;

            case R.id.database:
                Student student = new Student();
                student.setName("董志平");
                student.setId(123);
                student.setSex(1);
                student.setAge(90);
                if(studentDao.add(student) > 0){
                    toast("数据添加成功");
                }else{
                    toast("数据添加失败");
                }
                break;

            case R.id.requestPermission:
                toast("request permission now!");
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
                break;

            case R.id.checkUpdate:
                final NotifacationUtils notifacation = new NotifacationUtils(this);
                UpdateBean updateBean = new UpdateBean();
                updateBean.setDescription("今日更新了XBaseAndroid框架的更新管理器。");
                updateBean.setDownload_url("http://file.cleveriip.com:88/group2/M00/00/03/rBJbXVnlcPCATMAtAtnNwW8wwRs625.apk");
                updateBean.setForce(true);//是否强制更新 或 静默安装
                updateBean.setAuthority("com.hengyi.xbaseandroid.fileProvider");//兼容安卓7.0 安装
                updateBean.setMd5_code(null);
                updateBean.setNew_version("1.0.0.1");

                updateBean.setTitle("新版本来啦，立即更新吧");
                AppUpdateManager appUpdateManager = AppUpdateManager.getInstance();
                appUpdateManager.checkUpdate(updateBean,this);
                appUpdateManager.setAppUpdateListener(new AppUpdateManager.AppUpdateListener() {


                    @Override
                    public void downloadProgressBar(String progress, int progress2,String speed) {
                        notifacation.showProgressNotify(progress2,"当前下载网速" + speed);
                        Log.d("AppUpdateManager","进度条：" + progress + "  p2:"+ progress2 +"   下载速度："  + speed);
                    }

                    @Override
                    public void downloadSuccess(File app_path) {
                        Log.d("AppUpdateManager","下载成功    路径如下：" + app_path.getAbsolutePath());
                    }

                    @Override
                    public void downloadStart() {
                        Log.d("AppUpdateManager","下载开始");
                        notifacation.createProgressNotify(R.drawable.ic_launcher,200,"正在下载中","APP更新","App正在准备下载",new Intent());
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
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        toast(event.getString_str());
    };
}
