package com.hengyi.baseandroidcore.dialog;

import android.app.Dialog;
import android.content.Context;  
import android.view.Gravity;
import android.widget.TextView;

import com.hengyi.baseandroidcore.R;


public class CustomLoadingDialog extends Dialog {
	private TextView message;
	
    public CustomLoadingDialog(Context context, String strMessage) {
        this(context, R.style.loading_dialog, strMessage);
    }  
  
    public CustomLoadingDialog(Context context, int theme, String strMessage) {
        super(context, theme);  
        this.setContentView(R.layout.dialog_custom_loading);
        message = (TextView) findViewById(R.id.dialog_textview);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;  
        message.setText(strMessage);
    }  
  
    @Override  
    public void onWindowFocusChanged(boolean hasFocus) {  
        if (!hasFocus) {  
            dismiss();  
        }  
    }
    
    public void SetMessage(String Message){
    	message.setText(Message);
    }
}