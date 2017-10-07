package com.hengyi.db.dao;

import com.easydblib.dao.BaseDao;
import com.easydblib.info.WhereInfo;
import com.hengyi.db.table.Classes;
import com.hengyi.db.util.EasyDBHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ClassDao {
    private BaseDao<Classes> class_dao = null;


    public ClassDao(){
        class_dao = EasyDBHelper.getInstance().dao(Classes.class);
    }

    public boolean addClassList(List<Classes> class_list){
        int res = class_dao.add(class_list);
        return res > 0;
    }

    public void clearTable(){
        class_dao.clearTable();
    }

    public long count(){
        return class_dao.countOf();
    }

    public String getClassName(int class_id){
        List<Classes> data = class_dao.query(WhereInfo.get().equal("id",class_id).limit(1));
        if(data == null || data.size() == 0){
            return null;
        }else{
            return data.get(0).getName();
        }
    }
}
