package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hengyi.baseandroidcore.base.WebEngineActivity;
import com.hengyi.baseandroidcore.database.DatabaseHelper;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;
import com.hengyi.db.Student;
import com.hengyi.db.StudentDao;

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
                //ActivityStack.getInstance().popActivity();
                StudentDao sd = new StudentDao(getContext());
                Student s = new Student();
                s.setId(1);
                s.setName("ggeegegerger");
                int res = sd.add(s);
                toast("添加学生结果是：" + res);
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
