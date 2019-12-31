package com.hengyi.baseandroidcore.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static Random rand = new Random();

    //替换数组
    private static <T> void swap(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    //打乱数组
    public static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }
    
    //字符串翻转
	public static String reverse(String s) {
	    char[] array = s.toCharArray();
	    String reverse = "";
	    for (int i = array.length - 1; i >= 0; i--){
	        reverse += array[i];
	    }
	    return reverse;
	}

	//数组反转
    public static String[] reverseArray(String[] array){
        String [] newArray = new String[array.length];
        for(int i=0; i<newArray.length; i++){
            newArray[i] = array[array.length - i - 1];
        }
        return newArray;
    }
	
	//返回左移n位字符串方法
	public static String leftShift(String str,int position) {
		String str1 = str.substring(position);
		String str2 = str.substring(0, position);
		return str1+str2;
	}
		
		
	//返回右移n位字符串方法
	public static String rightShift(String str,int position) {
		String str1 = str.substring(str.length()-position);
		String str2 = str.substring(0, str.length()-position);
		return str1+str2;
	}
	
	//判断是否是字母或数字
	public static Boolean isWord(String text) {
		return text.matches("^[0-9A-Za-z]{1,}$");
	}
	
	//字符串转字符串数组
	public static String[] toStringArray(String text) {
		char[] textarr = text.toCharArray();
    	String[] arr = new String[textarr.length];
    	for(int i = 0;i<textarr.length;i++)
    		arr[i] = String.valueOf(textarr[i]);
    	return arr;
	}
	
	//字符首字母大写其余小写
	public static String textHeadtoUpperCase(String text) {
		 char[] chars = text.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
	}

    //字符首字母小写其余大写
    public static String textHeadtoLowerCase(String text) {
        char[] chars = text.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] = (char)(chars[0] + 32);
        }
        return new String(chars);
    }
	
	/***
	 * 下划线命名转为驼峰命名
	 * 
	 * @param para
	 *        下划线命名的字符串
	 */
    public static String underlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    
    /***
	 * 下划线命名转为驼峰命名(首字母大写)
	 *  SiChuan
	 * @param para
	 *        下划线命名的字符串
	 */
    public static String underlineToHumpHeadToUpper(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            result.append(s.substring(0, 1).toUpperCase());
            result.append(s.substring(1).toLowerCase());
        }
        return result.toString();
    }

	/***
	* 驼峰命名转为下划线命名
	 * 
	 * @param para
	 *        驼峰命名的字符串
	 */
    public String humpToUnderline(String para){
            StringBuilder sb=new StringBuilder(para);
            int temp=0;//定位
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
            return sb.toString().toUpperCase(); 
    }
    
    /**
     * 文本提取
     * @param regex
     * @param source
     * @param childCount
     * @return
     */
    public static List<String[]> getMatcher(String regex, String source,int childCount) {
        List<String[]> data = new ArrayList<String[]>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            String[] child = new String[childCount];
            for(int i = 1;i <= childCount;i++) {
                child[i - 1] = matcher.group(i);
            }
            data.add(child);
        }
        return data;
     }
    
    /**
     * 判空
     * @param text
     * @return
     */
    public static Boolean isEmpty(String text) {
    	if(text == null || text.length() == 0)
    		return true;
    	else
    		return false;
    }

    public static Boolean isBlank(String text){
        if(text == null){
            return true;
        }else {
            text = text.replaceAll(" ","");
            return text.equals("") || text.length() == 0;
        }
    }
	    
	public static void main(String[] args) {
		
	}
}
