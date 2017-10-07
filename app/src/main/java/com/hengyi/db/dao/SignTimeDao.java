package com.hengyi.db.dao;

import com.easydblib.dao.BaseDao;
import com.easydblib.info.WhereInfo;
import com.zhiweism.signterminal.db.table.SignTime;
import com.zhiweism.signterminal.db.util.EasyDBHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 */

public class SignTimeDao {
    private BaseDao<SignTime> sign_time_dao = null;

    public SignTimeDao(){
        sign_time_dao = EasyDBHelper.getInstance().dao(SignTime.class);
    }

    public void clearTable(){
        sign_time_dao.clearTable();
    }

    public boolean addSignTime(SignTime st){
        return sign_time_dao.add(st) > 0;
    }

    public long getCount(){
        return sign_time_dao.countOf();
    }

    public SignTime getNowSignTime(int hour){
        List<SignTime> sign_time_list = sign_time_dao.query(WhereInfo.get().ge("hour",hour).limit(1));
        if(sign_time_list == null || sign_time_list.size() == 0) {
            return null;
        }else{
            return sign_time_list.get(0);
        }
    }

    /**
     * 获取所有的考勤时间
     * @return
     */
    public List<SignTime> getAllSignTime(){
        List<SignTime> sign_time_list = sign_time_dao.queryAll();
        return sign_time_list;
    }
}
