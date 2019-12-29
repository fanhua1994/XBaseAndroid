package com.hengyi.baseandroidcore.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyi.baseandroidcore.R;

import org.w3c.dom.Text;

/**
 * 高仿微博弹出提示框
 */

public class CustomWeiboDialogUtils {
    private static TextView tvTitle;

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_loading_view);// 加载布局
        tvTitle = v.findViewById(R.id.tipTextView);// 提示文字
        tvTitle.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);//创建自定义样式dialog
        loadingDialog.setCancelable(true); //是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);

        return loadingDialog;
    }

    /**
     * 设置提示
     * @param msg
     */
    public static void setTitle(String msg){
        tvTitle.setText(msg);
    }

    /**
     * 显示dialog
     * @param dialog
     */
    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 影藏dialog
     * @param dialog
     */
    public static void hideDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.hide();
        }
    }

    /**
     * 关闭dialog
     * @param dialog
     */
    public static void closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}