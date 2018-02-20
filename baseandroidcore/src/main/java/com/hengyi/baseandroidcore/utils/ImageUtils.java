package com.hengyi.baseandroidcore.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.tools.GlideBlurTransformation;
import com.hengyi.baseandroidcore.tools.GlideCircleTransform;

import java.io.File;
import java.math.BigDecimal;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 网络图片的工具类
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
public class ImageUtils {
    private static ImageUtils instance = null;
    private static int loading_image = R.drawable.image_loading_default;
    private static int error_image = R.drawable.image_loading_error;


    public static synchronized ImageUtils getInstance(){
        synchronized (ImageUtils.class){
            if(instance == null)
                instance = new ImageUtils();


            return instance;
        }
    }

    public ImageUtils setImagePlaceholder(int loading, int error){
        error_image = loading;
        error_image = error;
        return this;
    }
    /**
     * (1)
     * 显示图片Imageview
     *
     * @param context  上下文
     * @param url      图片链接
     * @param imageview 组件
     */
    public void showImageView(Context context,String url,ImageView imageview) {
        Glide.with(context).load(url)// 加载图片
                .error(error_image)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(loading_image)// 设置占位图
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

    public void showImageViewGone(Context context,final ImageView imageView, String url) {
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
     * @param url
     * @param bgLayout
     */

    public void showImageView(Context context, String url,final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(error_image)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(loading_image)// 设置占位图
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

    public void showImageView(Context context, String url,
                                     final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(error_image)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(loading_image)// 设置占位图
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

    public void showImageView(Context context, String url,
                                     final FrameLayout frameBg) {
        Glide.with(context).load(url).asBitmap().error(error_image)// 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .placeholder(loading_image)// 设置占位图
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

    public void showImageViewBlur(Context context, String url, final RelativeLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(error_image)
                // 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(loading_image)
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

    public void showImageViewBlur(Context context, String url, final LinearLayout bgLayout) {
        Glide.with(context).load(url).asBitmap().error(error_image)
                // 设置错误图片

                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                // 缓存修改过的图片
                .placeholder(loading_image)
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
    public void showImageViewToCircle(Context context,String url,ImageView imageview) {
        Glide.with(context).load(url)
                // 加载图片
                .error(loading_image)
                // 设置错误图片
                .crossFade()
                // 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(error_image)
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
    public void showImageViewToCircleAndBlur(Context context,String url, ImageView imageview) {
        Glide.with(context).load(url)
                .error(error_image)// 设置错误图片
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
    public void showImageViewToRoundedCorners(Context context,String url, ImageView imageview) {
        Glide.with(context).load(url)
                .error(loading_image)// 设置错误图片
                .bitmapTransform(new RoundedCornersTransformation(context, 30, 0,
                        RoundedCornersTransformation.CornerType.ALL))// 设置矩形圆角
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageview);
    }

    //======================缓存清理=============================
    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
