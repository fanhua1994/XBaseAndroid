package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import android.view.View;

import com.devspark.appmsg.AppMsg;
import com.hengyi.baseandroidcore.base.BaseActivity;
import com.hengyi.baseandroidcore.base.BaseApplication;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.utils.ProjectUtils;
import com.hengyi.baseandroidcore.utilscode.Utils;
import com.hengyi.db.dao.StudentDao;
import com.hengyi.db.table.Student;

public class MainActivity extends BaseActivity {
    private StudentDao studentDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //弹出提示框

        CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
        dialog.setTitle("温馨提示");
        dialog.setMsg("你好啊");
        dialog.setNegativeButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("点击了确定");
                AppMsg.makeText(MainActivity.this,"点击了确定",AppMsg.STYLE_CONFIRM).show();
            }
        });

        dialog.setPositiveButton("取消",null);
        dialog.show();

        Student s = new Student();
        s.setAvatar("xxx");
        s.setClass_id(1);
        s.setName("打发");
        s.setSign_chip("453453453");
//
//        studentDao = new StudentDao();
//        boolean res = studentDao.addStudent(s);
//        toast("res:" + res);
        String project = ProjectUtils.getInstance().getRootDir();
        toast(project);
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_main;
    }
}
