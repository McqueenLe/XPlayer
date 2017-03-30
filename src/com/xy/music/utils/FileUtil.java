package com.xy.music.utils;

import java.text.DecimalFormat;

public class FileUtil {
	/**
     * 转换文件大小
     * 
     * @param fileS
     * @return
     */
    public static String convertFileSize(long size) { 
        DecimalFormat df = new DecimalFormat("#.00"); 
        String fileSizeString = ""; 
        if (size < 1024) { 
            fileSizeString = df.format((double) size) + "B"; 
        } else if (size < 1048576) { 
            fileSizeString = df.format((double) size / 1024) + "KB"; 
        } else if (size < 1073741824) { 
            fileSizeString = df.format((double) size / 1048576) + "MB"; 
        } else { 
            fileSizeString = df.format((double) size / 1073741824) + "G"; 
        } 
        return fileSizeString; 
    }  
}
