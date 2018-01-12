package com.hengyi.baseandroidcore.utils;



import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.hengyi.baseandroidcore.tools.DiskLruCache;
import com.hengyi.baseandroidcore.tools.DiskLruCacheUtil;
import com.hengyi.baseandroidcore.tools.DiskLruCacheUtils;

/**
 * 该类用于磁盘缓存。
 * @author 繁华
 */
public class DiskLruCacheHelper
{
	private static DiskLruCacheHelper instance = null;
    private static final String DIR_NAME = "diskCache";
    private static final int MAX_COUNT = 5 * 1024 * 1024;
	private static final int DEFAULT_VALUE_COUNT = 1;
	private static String NOW_APP_VERSION = null;
    private static DiskLruCache mDiskLruCache = null;
    private static Context con;
    private static boolean isInit = false;

    public static synchronized DiskLruCacheHelper getInstance(Context context)
    {
        synchronized (DiskLruCacheHelper.class) {
            if (instance == null) {
                instance = new DiskLruCacheHelper();
                mDiskLruCache = generateCache(context, DIR_NAME, MAX_COUNT);
                NOW_APP_VERSION = DiskLruCacheUtils.getAppVersionName(context);
                con = context;
            }

            return instance;
        }
    }

    private static DiskLruCache generateCache(Context context, String dirName, int maxCount)
    {
        DiskLruCache diskLruCache = null;
		try {
			diskLruCache = DiskLruCache.open(
			        getDiskCacheDir(context, dirName),
			        DiskLruCacheUtils.getAppVersion(context),
			        DEFAULT_VALUE_COUNT,
			        maxCount);
			isInit = true;
		} catch (IOException e) {
			isInit = false;
		}
        return diskLruCache;
    }
    
    /** 
     *  是否获得缓存权限
     *  @return
     */
    public boolean isGetCache(){
    	return isInit;
    }
    
    
    public String bytes2kb(long bytes) {  
        BigDecimal filesize = new BigDecimal(bytes);  
        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        if (returnValue > 1)  
            return (returnValue + "MB");  
        BigDecimal kilobyte = new BigDecimal(1024);  
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)  
                .floatValue();  
        return (returnValue + "KB");  
    }  
    
    
    /**
     * 计算缓存总大小 遍历文件夹下所有文件计算文件大小
     * @return
     */
    public String getCacheCount(){
    	long count = 0;
    	File dir = getDiskCacheDir(con, DIR_NAME);
    	
    	for(File file : dir.listFiles()){
    		count += file.length();
    	}
    	return bytes2kb(count);
    }
    
    
    /**
     * 由于删除缓存后diskLruCache会自动关闭缓存，
     * 导致下次使用缓存时出现异常。所以需要将单列重新
     * 获取。
     */
    public void deleteAllCache(){
    	try {
			//mDiskLruCache.delete();
			//instance = null;
    		/**
    		 * 直接调用底层删除方法，不需要重启缓存。之前系统方法
    		 * 需要先关闭，是为了考虑删除时，文件被占用问题。
    		 */
    		DiskLruCacheUtil.deleteContents(getDirectory());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // =======================================
    // ============== String 数据 读写 =============
    // =======================================

    public void put(String key, String value)
    {
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        try
        {
            edit = editor(key);
            if (edit == null) return;
            OutputStream os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(value);
            edit.commit();//提交事务
        } catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                //s
                edit.abort();//write REMOVE
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        } finally
        {
            try
            {
                if (bw != null)
                    bw.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getAsString(String key) {
        InputStream inputStream = null;
        inputStream = get(key);
        if (inputStream == null) return null;
        String str = null;
        try {
            str = DiskLruCacheUtil.readFully(new InputStreamReader(inputStream, DiskLruCacheUtil.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return str;
    }



    public void put(String key, JSONObject jsonObject)
    {
        put(key, jsonObject.toString());
    }

    public JSONObject getAsJson(String key)
    {
        String val = getAsString(key);
        try
        {
            if (val != null)
                return new JSONObject(val);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // =======================================
    // ============ JSONArray 数据 读写 =============
    // =======================================

    public void put(String key, JSONArray jsonArray)
    {
        put(key, jsonArray.toString());
    }

    public JSONArray getAsJSONArray(String key)
    {
        String JSONString = getAsString(key);
        try
        {
            JSONArray obj = new JSONArray(JSONString);
            return obj;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // =======================================
    // ============== byte 数据 读写 =============
    // =======================================

    /**
     * 保存 byte数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的数据
     */
    public void put(String key, byte[] value)
    {
        OutputStream out = null;
        DiskLruCache.Editor editor = null;
        try
        {
            editor = editor(key);
            if (editor == null)
            {
                return;
            }
            out = editor.newOutputStream(0);
            out.write(value);
            out.flush();
            editor.commit();//write CLEAN
        } catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                editor.abort();//write REMOVE
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }

        } finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    
    public byte[] getAsBytes(String key)
    {
        byte[] res = null;
        InputStream is = get(key);
        if (is == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            byte[] buf = new byte[256];
            int len = 0;
            while ((len = is.read(buf)) != -1)
            {
                baos.write(buf, 0, len);
            }
            res = baos.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return res;
    }


    // =======================================
    // ============== 序列化 数据 读写 =============
    // =======================================
    public void put(String key, Serializable value)
    {
        DiskLruCache.Editor editor = editor(key);
        ObjectOutputStream oos = null;
        if (editor == null) return;
        try
        {
            OutputStream os = editor.newOutputStream(0);
            oos = new ObjectOutputStream(os);
            oos.writeObject(value);
            oos.flush();
            editor.commit();
        } catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                editor.abort();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        } finally
        {
            try
            {
                if (oos != null)
                    oos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
	public <T> T getAsSerializable(String key)
    {
        T t = null;
        InputStream is = get(key);
        ObjectInputStream ois = null;
        if (is == null) return null;
        try
        {
            ois = new ObjectInputStream(is);
            t = (T) ois.readObject();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (ois != null)
                    ois.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return t;
    }

    // =======================================
    // ============== bitmap 数据 读写 =============
    // =======================================
    public void put(String key, Bitmap bitmap)
    {
        put(key, DiskLruCacheUtils.bitmap2Bytes(bitmap));
    }

    public Bitmap getAsBitmap(String key)
    {
        byte[] bytes = getAsBytes(key);
        if (bytes == null) return null;
        return DiskLruCacheUtils.bytes2Bitmap(bytes);
    }

    // =======================================
    // ============= drawable 数据 读写 =============
    // =======================================
    public void put(String key, Drawable value)
    {
        put(key, DiskLruCacheUtils.drawable2Bitmap(value));
    }

    public Drawable getAsDrawable(String key)
    {
        byte[] bytes = getAsBytes(key);
        if (bytes == null)
        {
            return null;
        }
        return DiskLruCacheUtils.bitmap2Drawable(DiskLruCacheUtils.bytes2Bitmap(bytes));
    }

    // =======================================
    // ============= other methods ===========
    // =======================================
    public boolean remove(String key)
    {
        try
        {
            key = DiskLruCacheUtils.hashKeyForDisk(NOW_APP_VERSION + key);
            return mDiskLruCache.remove(key);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void close() throws IOException
    {
        mDiskLruCache.close();
    }

    public void delete() throws IOException
    {
        mDiskLruCache.delete();
    }

    //刷新缓冲区
    public void flush() throws IOException
    {
        mDiskLruCache.flush();
    }

    public boolean isClosed()
    {
        return mDiskLruCache.isClosed();
    }

    public long size()
    {
        return mDiskLruCache.size();
    }

    public void setMaxSize(long maxSize)
    {
        mDiskLruCache.setMaxSize(maxSize);
    }

    public File getDirectory()
    {
        return mDiskLruCache.getDirectory();
    }

    public long getMaxSize()
    {
        return mDiskLruCache.getMaxSize();
    }

    // =======================================
    // ========遇到文件比较大的，可以直接通过流读写 ========
    // =======================================
    //basic editor
    public DiskLruCache.Editor editor(String key)
    {
        try
        {
        	//获得key
            key = DiskLruCacheUtils.hashKeyForDisk(NOW_APP_VERSION + key);
            //wirte DIRTY
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            //edit maybe null :the entry is editing
            return edit;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取输入流
     * @param key
     * @return
     */
    public InputStream get(String key)
    {
        try
        {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(DiskLruCacheUtils.hashKeyForDisk(NOW_APP_VERSION + key));
            if (snapshot == null) //not find entry , or entry.readable = false
            {
                return null;
            }
            //write READ
            return snapshot.getInputStream(0);

        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }


    // =======================================
    // ============== 序列化 数据 读写 ==============
    // =======================================
    
    /**
     * 获取数据目录
     * @param context
     * @param uniqueName
     * @return
     */
    private static File getDiskCacheDir(Context context, String uniqueName)
    {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && !Environment.isExternalStorageRemovable())
        {
            cachePath = context.getExternalCacheDir().getPath();
        } else
        {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}


