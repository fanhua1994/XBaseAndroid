package com.hengyi.baseandroiddemo;

import android.os.Bundle;
import com.hengyi.baseandroidcore.base.XBaseActivity;
import com.hengyi.baseandroidcore.event.EventMessage;

/**
 * Created: 2018/3/21 9:41
 * Author:fanhua
 * Email:90fanhua@gmail.com
 * Project:XBaseAndroid
 */

public class TestActivity extends XBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBaseContentView() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
