package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hengyi.baseandroidcore.base.WebEngineActivity;
import com.hengyi.baseandroidcore.database.DatabaseHelper;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.validation.ValidMsg;
import com.hengyi.baseandroidcore.validation.Validation;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;
import com.hengyi.db.Student;
import com.hengyi.db.StudentDao;
import com.hengyi.validation.User;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {
    @BindView(R.id.cache_admin)Button cache;
    @BindView(R.id.titleBar)EaseTitleBar easeTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //showLoadingDialog("正在加载");

        DatabaseHelper.addTable(Student.class);

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

    @OnClick({R.id.cache_admin,R.id.web})
    public void Click(View view){
        switch(view.getId()){
            case R.id.cache_admin:
                StartActivity(CacheActivity.class);
                break;

            case R.id.web:
                StartActivity(WebEngineActivity.class,new String[]{"url"},"http://www.baidu.com/");
                break;
        }
    }
}
