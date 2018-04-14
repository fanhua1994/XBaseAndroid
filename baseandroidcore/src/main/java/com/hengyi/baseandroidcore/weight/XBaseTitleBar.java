package com.hengyi.baseandroidcore.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengyi.baseandroidcore.R;

/**
 * title bar
 */
public class XBaseTitleBar extends RelativeLayout{
    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView rightText;
    protected TextView titleView;
    protected RelativeLayout titleLayout;

    public XBaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public XBaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public XBaseTitleBar(Context context) {
        super(context);
        init(context, null);
    }
    
    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.widget_title_bar, this);
        leftLayout = findViewById(R.id.left_layout);
        leftImage = findViewById(R.id.left_image);
        rightLayout = findViewById(R.id.right_layout);
        rightImage = findViewById(R.id.right_image);
        titleView = findViewById(R.id.title);
        titleLayout = findViewById(R.id.root);
        rightText = findViewById(R.id.right_text);
        
        parseStyle(context, attrs);
    }
    
    private void parseStyle(Context context, AttributeSet attrs){
        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XBaseTitleBar);
            String title = ta.getString(R.styleable.XBaseTitleBar_titleBarTitle);
            titleView.setText(title);
            
            Drawable leftDrawable = ta.getDrawable(R.styleable.XBaseTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.XBaseTitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            }
        
            Drawable background = ta.getDrawable(R.styleable.XBaseTitleBar_titleBarBackground);
            if(null != background) {
                titleLayout.setBackgroundDrawable(background);
            }
            
            ta.recycle();
        }
    }
    
    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }
    

    
    public void setLeftLayoutClickListener(OnClickListener listener){
        leftLayout.setOnClickListener(listener);
    }
    
    public void setRightLayoutClickListener(OnClickListener listener){
        rightLayout.setOnClickListener(listener);
    }

    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }

    public void setRightTextClickListener(OnClickListener listener){
        rightText.setOnClickListener(listener);
    }
    
    public void setLeftLayoutVisibility(int visibility){
        leftLayout.setVisibility(visibility);
    }
    
    public void setRightLayoutVisibility(int visibility){
        rightLayout.setVisibility(visibility);
    }
    
    public void setTitle(String title){
        titleView.setText(title);
    }

    public void setRightText(String mRightText){
        rightText.setText(mRightText);
    }
    
    public void setBackgroundColor(int color){
        titleLayout.setBackgroundColor(color);
    }
    
    public RelativeLayout getLeftLayout(){
        return leftLayout;
    }
    
    public RelativeLayout getRightLayout(){
        return rightLayout;
    }

    public void hideLeftLayout(){
        leftLayout.setVisibility(View.GONE);
    }

    public void hideRightLayout(){
        rightLayout.setVisibility(View.GONE);
    }
}
