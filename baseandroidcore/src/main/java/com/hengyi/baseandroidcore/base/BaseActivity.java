package com.hengyi.baseandroidcore.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Toast;

import com.hengyi.baseandroidcore.dialog.CustomLoadingDialog;
import com.hengyi.baseandroidcore.utils.ActivityStack;

import butterknife.ButterKnife;

/**
 * Created by 繁华 on 2017/5/14.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnTouchListener {
    private CustomLoadingDialog loading = null;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private boolean isNeedCheck = true;
    public Context context;
    private Intent intent;

    //手指向右滑动时的最小速度
    private static final int XSPEED_MIN = 500;
    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 250;
    //手指按下的起始最大位置
    private static final int STARTPOSITION_MAX = 100;
    //记录手指按下时的横坐标。
    private float xDown;
    //记录手指移动时的横坐标。
    private float xMove;
    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setBaseContentView());//设置布局文件
        ActivityStack.getInstance().pushActivity(this);//将界面加入堆栈
        context = this;//复制上下文
        ButterKnife.bind(this);//注入控件
        View view = getWindow().getDecorView();//获取跟视图
        view.setOnTouchListener(this);//设置触摸事件
    }

    public abstract int setBaseContentView();

    /**
     * 显示正在加载的提示
     * @param message
     */
    public void showLoadingDialog(String message){
        if(loading == null){
            loading = new CustomLoadingDialog(context,message);
        }else{
            loading.SetMessage(message);
        }
        loading.show();
    }

    /**
     * 关闭正在显示的提示
     */
    public void closeLoadingDialog(){
        if(loading != null){
            loading.cancel();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            ActivityStack.getInstance().popActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    //显示toast
    public void toast(String toast_text) {
        Toast.makeText(context, toast_text, Toast.LENGTH_LONG).show();
    }

    //杀死进程
    public void kill(){
        Process.killProcess(Process.myPid());
    }

    //界面跳转 不带参数
    public void StartActivity(Class cla) {
        intent = null;
        intent = new Intent(context, cla);
        startActivity(intent);
    }

    //带自定义参数
    public void StartActivity(Class cla, String[] names, Object... param) {
        intent = null;
        intent = new Intent(context, cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        startActivity(intent);
    }

    //带自定义参数  带回调
    public void StartActivity(Class cla, int result, String[] names, Object... param) {
        intent = null;
        intent = new Intent(context, cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        startActivityForResult(intent, result);
    }

    //启动服务
    public void StartService(Class cla){
        intent = null;
        intent = new Intent(context,cla);
        startService(intent);
    }

    public void StartService(Class cla,String[] names, Object... param){
        intent = null;
        intent = new Intent(context,cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        startService(intent);
    }

    public void CloseService(Class cla){
        intent = null;
        intent = new Intent(context,cla);
        stopService(intent);
    }

    //发送广播
    public void SendBroadCast(Class cla,String[] names, Object... param){
        intent  = null;
        intent = new Intent(context,cla);
        for (int i = 0; i < param.length; i++) {
            if (param[i].getClass().equals(Integer.class)) {
                intent.putExtra(names[i], (Integer) param[i]);
            } else if (param[i].getClass().equals(String.class)) {
                intent.putExtra(names[i], (String) param[i]);
            } else if (param[i].getClass().equals(Boolean.class)) {
                intent.putExtra(names[i], (Boolean) param[i]);
            } else if (param[i].getClass().equals(Float.class)) {
                intent.putExtra(names[i], (Float) param[i]);
            } else if (param[i].getClass().equals(Double.class)) {
                intent.putExtra(names[i], (Double) param[i]);
            }
        }
        sendBroadcast(intent);
    }


    public void SendBroadCast(Class cla){
        intent  = null;
        intent = new Intent(context,cla);
        sendBroadcast(intent);
    }

    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                //活动的距离
                int distanceX = (int) (xMove - xDown);
                //获取顺时速度
                int xSpeed = getScrollVelocity();
                //当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
                if(xDown < STARTPOSITION_MAX && distanceX > XDISTANCE_MIN  && xSpeed > XSPEED_MIN) {
                    recycleVelocityTracker();
                    ActivityStack.getInstance().popActivity(this);//退出当前界面
                }

                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
