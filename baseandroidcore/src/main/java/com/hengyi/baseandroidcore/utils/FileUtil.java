package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/13.
 */

public class FileUtil {
    public static final int COMMON_FILE = 0;
    public static final int DB_FILE  = 1;
    public static final int CACHE_FILE = 2;

    private static  FileUtil instance;
    private static boolean IdCardStatus = false;
    private int file_type = 0;
    private Context context = null;

    public static FileUtil getInstance(){
        if(instance == null) {
            instance = new FileUtil();
            IdCardStatus = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
        return instance;
    }

    //=============创建方法========================
    public boolean createWorkGroup(String group_name){
        File dir = new File(this.getWorkDir() + File.separator + group_name);
        if(!dir.exists()){
            return dir.mkdirs();
        }else{
            return true;
        }
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setFileType(int file_type){
        this.file_type = file_type;
    }

    public void setIdCard(boolean idcard){
        this.IdCardStatus = idcard;
    }



    //================删除方法========================
    //删除工程组
    public boolean deleteWorkGroup(String group_name){
        File dir2 = new File(this.getWorkDir() + File.separator + group_name);
        if(dir2.exists()){
            File[] files = dir2.listFiles();
            for(File f: files){
                f.delete();
            }
            dir2.delete();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除子目录文件
     * @param group_name
     * @param filename
     * @return
     */
    public boolean deleteWorkGroupFile(String group_name,String filename){
        File file2 = new File(this.getWorkGroup(group_name) + File.separator + filename);
        if(file2.exists()){
            return file2.delete();
        }else{
            return false;
        }
    }

    /**
     * 删除工程文件
     * @param filename
     * @return
     */
    public boolean deleteWorkFile(String filename){
        File file1 = this.getWorkFile(filename);
        if(file1.exists()){
            return file1.delete();
        }else{
            return false;
        }
    }
    //================获取方法========================
    public boolean getIdCardStatus(){
        return IdCardStatus;
    }

    /**
     * 获取工作目录
     */
    public String getWorkDir(){
        if(context == null)
            return null;

        String workDir = null;
        if(IdCardStatus){
            switch(file_type){
                case COMMON_FILE:
                    workDir = context.getExternalCacheDir().getParentFile().getAbsolutePath();
                    break;

                case DB_FILE:
                    workDir = context.getExternalCacheDir().getParentFile().getAbsolutePath() + File.separator +"db";
                    break;

                case CACHE_FILE:
                    workDir = context.getExternalCacheDir().getAbsolutePath();
                    break;
            }
        }else{
            switch(file_type) {
                case COMMON_FILE:
                    workDir = context.getCacheDir().getAbsolutePath();
                    break;

                case DB_FILE:
                    workDir = context.getDatabasePath("db").getAbsolutePath();
                    break;

                case CACHE_FILE:
                    workDir = context.getCacheDir().getAbsolutePath();
                    break;
            }
        }

        return workDir;
    }

    /**
     * 获取一个File对象
     * 没有分组
     * @param filename
     * @return
     */
    public File getWorkFile(String filename){
        File files = new File(getWorkDir() + File.separator + filename);
        return files;
    }

    /**
     * 自动创建
     * @param filename
     * @param auto_create
     * @return
     */
    public File getWorkFile(String filename,boolean auto_create){
        File files = new File(getWorkDir() + File.separator + filename);
        if(auto_create && ! files.exists()){
            try {
                files.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    /**
     * 获取一个File对象
     * @param filename
     * @return
     */
    public File getWorkGroupFile(String group_name,String filename){
        File files = new File(getWorkGroup(group_name) + File.separator + filename);
        return files;
    }

    public File getWorkGroupFile(String group_name,String filename,boolean auto_create){
        File files = new File(getWorkGroup(group_name) + File.separator + filename);
        if(auto_create && ! files.exists()){
            try {
                files.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    /**
     * 获取一个默认的File对象
     * @return
     */
    public File getDefaultWorkFile(String Suffix){
        String filename = MD5.get(new Date().toString())+ "." + Suffix;
        File files = new File(getWorkDir() + File.separator + filename);
        return files;
    }

    public String getWorkGroup(String group_name){
        createWorkGroup(group_name);
        return this.getWorkDir() + File.separator + group_name;
    }

    public File getWorkGroupDirFile(String group_name){
        createWorkGroup(group_name);
        return new File(this.getWorkDir() + File.separator + group_name);
    }

    public File getDefaultWorkGroupFile(String dirname,String Suffix){
        String filename = MD5.get(new Date().toString())+ "." + Suffix;
        File files = new File(getWorkDir() + File.separator + dirname + File.separator + filename);
        return files;
    }

    public String getPathToFilename(String path){
        return path.substring(path.lastIndexOf("/"),path.length());
    }

    /**
     * 获取文件列表
     */

    public File[] getWorkGroupDirFileList(String group_name){
        File dir = getWorkGroupDirFile(group_name);
        return dir.listFiles();
    }


    //===========公用方法======================
    public void writeFile(File file,String content,boolean add){
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(file.getAbsolutePath(), add);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     * @param is_delete 读取后删除源文件
     * @return
     */
    public String readFile(File file,boolean is_delete){
        try {
            if(!file.exists()){
                return null;
            }
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while(( len = in.read(buffer)) != -1){
                sb.append(new String(buffer,0,len));
            }
            in.close();
            file.delete();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deletePublicFile(String filepath){
        File file3 = new File(filepath);
        if(file3.exists()){
            return file3.delete();
        }else{
            return false;
        }
    }

    public boolean deletePublicFile(File file){
        if(file.exists()){
            return file.delete();
        }else{
            return false;
        }
    }

}
