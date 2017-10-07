package com.hengyi.db.dao;

import com.easydblib.dao.BaseDao;
import com.easydblib.info.OrderInfo;
import com.easydblib.info.WhereInfo;
import com.zhiweism.signterminal.db.table.Classes;
import com.zhiweism.signterminal.db.table.SignTask;
import com.zhiweism.signterminal.db.util.EasyDBHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class SignTaskDao {
    private BaseDao<SignTask> sign_task_dao = null;


    public SignTaskDao(){
        sign_task_dao = EasyDBHelper.getInstance().dao(SignTask.class);
    }

    //添加一条任务
    public boolean addSignTask(SignTask st){
        int res = sign_task_dao.add(st);
        return res > 0;
    }

    public boolean removeSignTask(SignTask st){
        int res = sign_task_dao.delete(st);
        return res > 0;
    }

    public SignTask getOneSignTask(){
        List<SignTask> signTaskList = sign_task_dao.query(WhereInfo.get().limit(1));
        if(signTaskList == null || signTaskList.size() == 0){
            return null;
        }else{
            return signTaskList.get(0);
        }
    }
}
