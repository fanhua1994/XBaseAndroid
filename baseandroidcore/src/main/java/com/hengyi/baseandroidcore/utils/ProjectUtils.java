package com.hengyi.baseandroidcore.utils;

import android.os.Environment;


import com.hengyi.baseandroidcore.base.XBaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by 繁华 on 2017/9/13.
 */

public class ProjectUtils {
    public static final int ROOT_TPYE = 0;
    public static final int COMMON_TYPE = 1;
    public static final int DB_TYPE  = 2;
    public static final int CACHE_TYPE = 3;
    public static final int FILE_TYPE = 4;

    private static ProjectUtils instance;
    private static boolean IdCardStatus = false;
    private int fileType = 0;

    public static ProjectUtils getInstance() {
        if (instance == null) {
            instance = new ProjectUtils();
            IdCardStatus = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
        }
        return instance;
    }

    //=============创建方法========================
    public boolean createWorkGroup(String groupName){
        File dir = new File(this.getWorkDir() + File.separator + groupName);
        if(!dir.exists()){
            return dir.mkdirs();
        }else{
            return true;
        }
    }

    public ProjectUtils setFileType(int fileType){
        this.fileType = fileType;
        return this;
    }

    public ProjectUtils setIdCard(boolean idcard) {
        if(getIdCardStatus())
        this.IdCardStatus = idcard;
        return this;
    }

    private String getDefalutName(String Suffix) {
        return Md5Utils.get(Math.random()+":" + System.currentTimeMillis())+"."+Suffix;
    }

    /**
     * 默认生成文件名 返回文件File,此类文件没有后缀
     * @param groupName
     * @param content
     * @return
     */
    public File writeWorkGroup(String groupName,String suffix,String content){
        File files = this.getWorkGroupFile(groupName,getDefalutName(suffix),true);
        writeFile(files,content);
        return files;
    }
    //================删除方法========================
    //清空工程组
    public boolean clearWorkGroup(String groupName){
        File dir2 = new File(this.getWorkDir() + File.separator + groupName);
        if(dir2.exists()){
            File[] files = dir2.listFiles();
            for(File f : files){
                f.delete();
            }
            return dir2.delete();
        }else{
            return false;
        }
    }

    //清空工程组
    public boolean clearWorkGroup(String groupName,boolean deleleWorkGroup){
        File dir2 = new File(this.getWorkDir() + File.separator + groupName);
        if(dir2.exists()){
            File[] files = dir2.listFiles();
            for(File f: files){
                f.delete();
            }
            if(deleleWorkGroup)
            dir2.delete();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除子目录文件
     * @param groupName
     * @param filename
     * @return
     */
    public boolean deleteWorkGroupFile(String groupName,String filename){
        File file2 = new File(this.getWorkGroup(groupName) + File.separator + filename);
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
        String workDir = null;
        if(IdCardStatus){
            switch(fileType){
                case COMMON_TYPE:
                    workDir = XBaseApplication.getApplication().getExternalCacheDir().getParentFile().getAbsolutePath();
                    break;

                case DB_TYPE:
                    workDir = XBaseApplication.getApplication().getExternalCacheDir().getParentFile().getAbsolutePath() + File.separator +"database";
                    break;

                case CACHE_TYPE:
                    workDir = XBaseApplication.getApplication().getExternalCacheDir().getAbsolutePath();
                    break;

                case FILE_TYPE:
                    workDir = XBaseApplication.getApplication().getExternalFilesDir("db").getParentFile().getAbsolutePath();
                    break;

                case ROOT_TPYE:
                    workDir = getRootDir();
                    break;
            }
        }else{
            switch(fileType) {
                case COMMON_TYPE:
                    workDir = XBaseApplication.getApplication().getCacheDir().getParentFile().getAbsolutePath();
                    break;

                case DB_TYPE:
                    workDir = XBaseApplication.getApplication().getDatabasePath("db").getParentFile().getAbsolutePath();
                    break;

                case CACHE_TYPE:
                    workDir = XBaseApplication.getApplication().getCacheDir().getAbsolutePath();
                    break;

                case FILE_TYPE:
                    workDir = XBaseApplication.getApplication().getFilesDir().getAbsolutePath();
                    break;
            }
        }

        File workdir  = new File(workDir);
        if(!workdir.exists())
            workdir.mkdirs();

        return workDir;
    }

    /**
     * 获取内存卡根目录
     * @return
     */
    public String getRootDir(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
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
    public File getWorkGroupFile(String groupName,String filename){
        File files = new File(getWorkGroup(groupName) + File.separator + filename);
        return files;
    }

    public File getWorkGroupFile(String groupName,String filename,boolean auto_create){
        File files = new File(getWorkGroup(groupName) + File.separator + filename);
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
        String filename = Md5Utils.get(new Date().toString())+ "." + Suffix;
        File files = new File(getWorkDir() + File.separator + filename);
        return files;
    }

    public String getWorkGroup(String groupName){
        createWorkGroup(groupName);
        return this.getWorkDir() + File.separator + groupName;
    }

    public File getWorkGroupFile(String groupName){
        createWorkGroup(groupName);
        return new File(this.getWorkDir() + File.separator + groupName);
    }

    public File getDefaultWorkGroupFile(String groupName,String Suffix){
        String filename = Md5Utils.get(new Date().toString())+ "." + Suffix;
        File files = new File(getWorkDir() + File.separator + groupName + File.separator + filename);
        return files;
    }

    public String getPathToFilename(String path){
        return path.substring(path.lastIndexOf("/"),path.length());
    }

    /**
     * 获取文件列表
     */

    public File[] getWorkGroupFileList(String groupName){
        File dir = getWorkGroupFile(groupName);
        return dir.listFiles();
    }


    //===========公用方法======================
    public void writeFile(File file,String content){
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
    public String readFile(File file,boolean isDelete){
        String content = readFile(file);
        if(isDelete)
            file.delete();
        return content;
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
