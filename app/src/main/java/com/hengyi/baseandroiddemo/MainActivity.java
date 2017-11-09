package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hengyi.baseandroidcore.base.BaseWebActivity;
import com.hengyi.baseandroidcore.database.DatabaseHelper;
import com.hengyi.baseandroidcore.database.DatabaseVersionChangeListener;
import com.hengyi.baseandroidcore.utils.CountDownUtils;
import com.hengyi.baseandroidcore.validation.ValidMsg;
import com.hengyi.baseandroidcore.validation.Validation;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;
import com.hengyi.db.Student;
import com.hengyi.db.StudentDao;
import com.hengyi.validation.User;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                toast("数据库版本繁盛变化：老版本:"+oldVersion +" 新版本："+newVersion);
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

    @OnClick({R.id.cache_admin,R.id.web,R.id.database})
    public void Click(View view){
        switch(view.getId()){
            case R.id.cache_admin:
                StartActivity(CacheActivity.class);
                break;

            case R.id.web:
                StartActivity(BaseWebActivity.class,new String[]{BaseWebActivity.WEB_URL_PARAM, BaseWebActivity.WEB_SHOW_TITLE_BAR},"file:///android_asset/index.html",false);
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
        }
    }
}
