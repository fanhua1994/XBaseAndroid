package com.hengyi.baseandroiddemo;


import android.os.Bundle;
import android.widget.ImageView;


import com.hengyi.baseandroidcore.utils.GlideUtils;

import butterknife.BindView;

public class ImageActivity extends BaseActivity {
    @BindView(R.id.iv_circle_image)
    ImageView circleImage;

    @BindView(R.id.iv_normal_image)
    ImageView normalImage;

    @Override
    public int setContentView() {
        return R.layout.activity_image;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlideUtils.loadCircleImage(this,"https://xbaseweb-file-cdn.oss-cn-chengdu.aliyuncs.com/2019-11-22/3315457eba4b799ac08e62937da4d44c.png",circleImage);
        GlideUtils.loadImage(this,"https://xbaseweb-file-cdn.oss-cn-chengdu.aliyuncs.com/2019-11-22/3315457eba4b799ac08e62937da4d44c.png",normalImage);
    }
}
