package com.hengyi.baseandroidcore.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.tools.GlideBlurTransformation;
import com.hengyi.baseandroidcore.tools.GlideCircleTransform;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 网络图片的工具类
 * <p>
 * (1)ImageView设置图片（错误图片）
 * （2）ImageView设置图片---BitMap(不设置默认图)
 * （3）设置RelativeLayout
 * （4）设置LinearLayout
 * （5）设置FrameLayout
 * （6）高斯模糊------ RelativeLayout
 * （7）高斯模糊------ LinearLayout
 * （8）圆角显示图片  ImageView
 * 使用工具类
 * （9）多种样式（模糊+圆角）
 *
 * @author huangshuyuan
 */
public class GlideImageUtils {

    /**
     * (1)
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imageview 组件
     */
    public static void showImageView(Context context,String url,ImageView imageview) {
        Glide.with(context).load(url)// 加载图片
                .error(R.drawable.image_loading_error)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(R.drawable.image_loading_default)// 设置占位图
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageview);
    }

    /**
     * （2）
     * 获取到Bitmap---不设置错误图片，错误图片不显示
     *
     * @param context
     * @param imageView
     * @param url
     */

    public static void showImageViewGone(Context context,final ImageView imageView, String url) {
        Glide.with(context).load(url).asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
            .into(new SimpleTarget<Bitmap>() {

                @SuppressLint("NewApi")
                @Override
                public void onResourceReady(Bitmap loadedImage,
                                            GlideAnimation<? super Bitmap> arg1) {

                    imageView.setVisibility(View.VISIBLE);
                    BitmapDrawable bd = new BitmapDrawable(loadedImage);
                    imageView.setImageDrawable(bd);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    // TODO Auto-generated method stub
                    super.onLoadFailed(e, errorDrawable);
                    imageView.setVisibility(View.GONE);
                }

            });

    }

    /**
     * （3）
     * 设置RelativeLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param errorimg
     * @param url
     * @param bgLayout
     */

    public static void showImageView(Context context, String url,final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(R.drawable.image_loading_error)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(R.drawable.image_loading_default)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （4）
     * 设置LinearLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param url
     * @param bgLayout
     */

    public static void showImageView(Context context, String url,
                                     final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(R.drawable.image_loading_error)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(R.drawable.image_loading_default)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （5）
     * 设置FrameLayout
     * <p>
     * 获取到Bitmap
     *
     * @param context
     * @param url
     * @param frameBg
     */

    public static void showImageView(Context context, String url,
                                     final FrameLayout frameBg) {
        Glide.with(context).load(url).asBitmap().error(R.drawable.image_loading_error)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(R.drawable.image_loading_default)// 设置占位图
                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        frameBg.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        frameBg.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （6）
     * 获取到Bitmap 高斯模糊         RelativeLayout
     *
     * @param context
     * @param url
     * @param bgLayout
     */

    public static void showImageViewBlur(Context context, String url, final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(R.drawable.image_loading_error)
                // 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(R.drawable.image_loading_default)
                .transform(new GlideBlurTransformation(context))// 高斯模糊处理
                // 设置占位图

                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （7）
     * 获取到Bitmap 高斯模糊 LinearLayout
     *
     * @param context
     * @param url
     * @param bgLayout
     */

    public static void showImageViewBlur(Context context, String url, final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(R.drawable.image_loading_error)
                // 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(R.drawable.image_loading_default)
                .transform(new GlideBlurTransformation(context))// 高斯模糊处理
                // 设置占位图

                .into(new SimpleTarget<Bitmap>() {

                    @SuppressLint("NewApi")
                    @Override
                    public void onResourceReady(Bitmap loadedImage,
                                                GlideAnimation<? super Bitmap> arg1) {
                        BitmapDrawable bd = new BitmapDrawable(loadedImage);

                        bgLayout.setBackground(bd);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // TODO Auto-generated method stub
                        super.onLoadFailed(e, errorDrawable);

                        bgLayout.setBackgroundDrawable(errorDrawable);
                    }

                });

    }

    /**
     * （8）
     * 显示图片 圆角显示  ImageView
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imageview 组件
     */
    public static void showImageViewToCircle(Context context,String url,ImageView imageview) {
        Glide.with(context).load(url)
                // 加载图片
                .error(R.drawable.image_loading_default)
                // 设置错误图片
                .crossFade()
                // 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(R.drawable.image_loading_error)
                // 设置占位图
                .transform(new GlideCircleTransform(context))//圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageview);

    }
    /**
     * （9）
     * 多种样式（模糊+圆角） ImageView
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imageview 组件
     */
    public static void showImageViewToCircleAndBlur(Context context,String url, ImageView imageview) {
        Glide.with(context).load(url)
                .error(R.drawable.image_loading_error)// 设置错误图片
                .bitmapTransform(new BlurTransformation(context, 15), new CropCircleTransformation(context))// 设置高斯模糊，圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageview);


    }

    /**
     * （10）
     * 矩形圆角 ImageView
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imageview 组件
     */
    public static void showImageViewToRoundedCorners(Context context,String url, ImageView imageview) {
        Glide.with(context).load(url)
                .error(R.drawable.image_loading_default)// 设置错误图片
                .bitmapTransform(new RoundedCornersTransformation(context, 30, 0,
                        RoundedCornersTransformation.CornerType.ALL))// 设置矩形圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageview);
    }
}
