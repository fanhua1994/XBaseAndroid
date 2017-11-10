package com.hengyi.baseandroiddemo;

import android.os.Bundle;

import com.hengyi.baseandroidcore.base.XbaseActivity;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.utils.DiskLruCacheHelper;

/**
 * Created by Administrator on 2017/10/8.
 */

public class CacheActivity extends BaseActivity {

    @Override
    public int setContentView() {
        return R.layout.activity_cache;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DiskLruCacheHelper cache = DiskLruCacheHelper.getInstance(this);
//        cache.put("cache","21424984034324934320434940494044904239");
//        toast(cache.getAsString("cache") +"缓存大小："+cache.getCacheCount());
    }
}
