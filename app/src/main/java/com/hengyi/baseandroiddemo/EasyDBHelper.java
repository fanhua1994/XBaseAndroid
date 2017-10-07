package com.hengyi.baseandroiddemo;


import com.easydblib.helper.BaseDBHelper;
import com.hengyi.baseandroidcore.base.BaseApplication;
import com.hengyi.baseandroidcore.utils.FileUtil;

import java.sql.SQLException;

/**
 *
 * 数据库辅助类
 * @author : zhousf
 */
public class EasyDBHelper extends BaseDBHelper {

	//版本号
	private static final int DB_VERSION = 1;

	//数据库存放路径
	private static final String DB_PATH = FileUtil.getInstance().getWorkDir();

	//数据库名称
	private static final String DB_NAME = "app.db";

	//数据表清单
	private static Class<?>[] tables = {};

	private static EasyDBHelper helper = null;

	public static EasyDBHelper getInstance(){
		if(null == helper){
			synchronized (EasyDBHelper.class){
				if(null == helper){
					helper = new EasyDBHelper();
				}
			}
		}
		return helper;
	}

	private EasyDBHelper() {
		//系统数据库
//		super(BaseApplication.getApplication(), null,DB_NAME,DB_VERSION,tables);
		//SD卡数据库
		super(BaseApplication.getApplication(),DB_PATH,DB_NAME,DB_VERSION,tables);
	}

	@Override
	protected BaseDBHelper initHelper() {
		return getInstance();
	}

	@Override
	protected boolean upgrade(int oldVersion, int newVersion) throws SQLException {
//		if(oldVersion < 2){
//			//增加字段ext
//			getDao(SimpleData.class).executeRaw("ALTER TABLE'simpledata' ADD COLUMN ext TEXT DEFAULT 'default';");
//		}
//		if(oldVersion < 3){
//			//增加字段sta
//			getDao(SimpleData.class).executeRaw("ALTER TABLE'simpledata' ADD COLUMN sta TEXT DEFAULT 'default';");
//		}
		return true;
	}

}
