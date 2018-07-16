package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XBaseActivity;
import com.hengyi.baseandroidcore.utils.ProjectUtils;

import java.io.File;

/**
 * Created：2018/7/5
 * Time：21:09
 * Author：dongzp
 * Email：90fanhua@gmail.com
 * Project：XBaseAndroid
 * Use：
 */
public class WorkGroupActivity extends XBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File("sss");//演示File
        ProjectUtils projectUtils = ProjectUtils.getInstance().setFileType(ProjectUtils.FILE_TYPE).setIdCard(true);
        projectUtils.createWorkGroup("images");//返回是否创建工作组成功
        projectUtils.clearWorkGroup("images");//清空工作组里的所有文件
        projectUtils.deleteWorkGroupFile("images","1.txt");//删除工作组里的1.txt
        projectUtils.getDefaultWorkFile("txt");//该放大将返回一个无工作组ROOT文件对象。且文件名随机。
        projectUtils.getDefaultWorkGroupFile("images","png");//返回一个File对象。工作组不存在不会自动创建
        projectUtils.getWorkGroupFileList("images");//获取一个工作组下所有文件对象。
        projectUtils.writeFile(file,"你啊或");//往一个File对象写入文本。
        projectUtils.writeWorkGroup("images","txt","自动将文本写入到images");//返回一个File对象

    }

    @Override
    public void onPermissionSuccess() {

    }

    @Override
    public void onPermissionError(String[] deniedPermissions) {

    }

    @Override
    public int setBaseContentView() {
        return 0;
    }
}
