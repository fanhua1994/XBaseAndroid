package com.hengyi.baseandroidcore.dialog;

import android.app.Dialog;
import android.content.Context;  
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengyi.baseandroidcore.R;

import static com.hengyi.baseandroidcore.R.id.dialog_imageview;


public class CustomLoadingDialog extends Dialog {
	private TextView message;
	private ImageView loading_image;
	
    public CustomLoadingDialog(Context context, String strMessage) {
        this(context, R.style.loading_dialog, strMessage);
    }  
  
    public CustomLoadingDialog(Context context, int theme, String strMessage) {
        super(context, theme);  
        this.setContentView(R.layout.dialog_custom_loading);
        message = findViewById(R.id.dialog_textview);
        loading_image = findViewById(R.id.dialog_imageview);

        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        message.setText(strMessage);

        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_image_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if(operatingAnim!=null){
            loading_image.startAnimation(operatingAnim);
        }
    }  
  
    @Override  
    public void onWindowFocusChanged(boolean hasFocus) {  
        if (!hasFocus) {
            loading_image.clearAnimation();
            dismiss();  
        }  
    }
    
    public void SetMessage(String Message){
    	message.setText(Message);
    }
}