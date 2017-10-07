package com.hengyi.baseandroidcore.utils;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ProjectUtil {
    public static final int COMMON_TYPE = 0;
    public static final int DB_TYPE  = 1;
    public static final int CACHE_TYPE = 2;
    public static final int FILE_TYPE = 3;

    private static ProjectUtil instance;
    private static boolean IdCardStatus = false;
    private int file_type = 0;
    private Context context = null;

    public static ProjectUtil getInstance(){
        if(instance == null) {
            instance = new ProjectUtil();
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

    public ProjectUtil setContext(Context context){
        this.context = context;
        return this;
    }

    public ProjectUtil setFileType(int file_type){
        this.file_type = file_type;
        return this;
    }

    public ProjectUtil setIdCard(boolean idcard) {
        this.IdCardStatus = idcard;
        return this;
    }

    private String getDefalutName() {
        return MD5.get(Math.random()+":" + System.currentTimeMillis());
    }

    private String getDefalutName(String Suffix) {
        return MD5.get(Math.random()+":" + System.currentTimeMillis())+"."+Suffix;
    }

    /**
     * 往一个分组写入数据

     * @param group_name
     * @return
     */
    public void writeWorkGroup(String filename,String group_name,String content){
        File files = this.getWorkGroupFile(group_name,filename,true);
        writeFile(files,content,false);
    }

    /**
     * 默认生成文件名 返回文件File,此类文件没有后缀
     * @param group_name
     * @param content
     * @return
     */
    public File writeWorkGroup(String group_name,String content){
        File files = this.getWorkGroupFile(group_name,getDefalutName(),true);
        writeFile(files,content,false);
        return files;
    }
    //================删除方法========================
    //清空工程组
    public boolean clearWorkGroup(String group_name){
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

    //清空工程组
    public boolean clearWorkGroup(String group_name,boolean delele_work_group){
        File dir2 = new File(this.getWorkDir() + File.separator + group_name);
        if(dir2.exists()){
            File[] files = dir2.listFiles();
            for(File f: files){
                f.delete();
            }
            if(delele_work_group)
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
                case COMMON_TYPE:
                    workDir = context.getExternalCacheDir().getParentFile().getAbsolutePath();
                    break;

                case DB_TYPE:
                    workDir = context.getExternalCacheDir().getParentFile().getAbsolutePath() + File.separator +"database";
                    break;

                case CACHE_TYPE:
                    workDir = context.getExternalCacheDir().getAbsolutePath();
                    break;

                case FILE_TYPE:
                    workDir = context.getExternalFilesDir("db").getParentFile().getAbsolutePath();
                    break;
            }
        }else{
            switch(file_type) {
                case COMMON_TYPE:
                    workDir = context.getCacheDir().getParentFile().getAbsolutePath();
                    break;

                case DB_TYPE:
                    workDir = context.getDatabasePath("db").getParentFile().getAbsolutePath();
                    break;

                case CACHE_TYPE:
                    workDir = context.getCacheDir().getAbsolutePath();
                    break;

                case FILE_TYPE:
                    workDir = context.getFilesDir().getAbsolutePath();
                    break;
            }
        }

        File workdir  = new File(workDir);
        if(!workdir.exists())
            workdir.mkdirs();

        return workDir;
    }

    /**
     * 获取根缓存目录、数据库目录、其他目录
     */

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

    public File getWorkGroupFile(String group_name){
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
        File dir = getWorkGroupFile(group_name);
        return dir.listFiles();
    }


    //===========公用方法======================
    public void writeFile(File file,String content,boolean add){
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
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
            if(is_delete)
            file.delete();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String readFile(File file){
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
