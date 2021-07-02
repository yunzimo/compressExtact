package com.yunzimo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class compressExtract
{

    public static String[] allowTypes = new String[] { ".txt", ".png", "gif", ".img",".doc" };
    /**
     * 根据给定密码压缩文件(s)到指定目录
     *
     * @param destFileName 压缩文件存放绝对路径 e.g.:D:/upload/zip/demo.zip
     * @param passwd 密码(可为空)
     * @param files 单个文件或文件数组
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String compress(String destFileName, String passwd, File... files) {
        ZipParameters parameters = getZipParameters(passwd);
        try {
            ZipFile zipFile = new ZipFile(destFileName);
            for (File file : files) {
                if(isValid(file.getName(),compressExtract.allowTypes)){
                    zipFile.addFile(file, parameters);
                }
            }
            return destFileName;
        } catch (ZipException e) {
            e.printStackTrace();
        }

        return null;
    }

    //获取压缩包所需要的参数
    private static ZipParameters getZipParameters(String passwd) {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
        if (!"".equals(passwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        return parameters;
    }

    /**
     * 根据给定密码压缩文件(s)到指定目录
     *
     * @param destFileName 压缩文件存放绝对路径 e.g.:D:/upload/zip/demo.zip
     * @param passwd 密码(可为空)
     * @param file 单个文件
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String compress(String destFileName, String passwd, File file) {
        ZipParameters parameters = getZipParameters(passwd);
        try {
            ZipFile zipFile = new ZipFile(destFileName);
            System.out.println(file.getName());
            if(isValid(file.getName(),compressExtract.allowTypes)){
                zipFile.addFile(file, parameters);
            }
            return destFileName;
        } catch (ZipException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据给定密码压缩文件(s)到指定位置
     *
     * @param destFileName 压缩文件存放绝对路径 e.g.:D:/upload/zip/demo.zip
     * @param passwd 密码(可为空)
     * @param filePaths 单个文件路径或文件路径数组
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String compress(String destFileName, String passwd, String... filePaths) {
        int size = filePaths.length;
        File[] files = new File[size];
        for (int i = 0; i < size; i++) {
            files[i] = new File(filePaths[i]);
        }
        return compress(destFileName, passwd, files);
    }

    /**
     * 根据给定密码压缩文件(s)到指定位置
     *
     * @param destFileName 压缩文件存放绝对路径 e.g.:D:/upload/zip/demo.zip
     * @param passwd 密码(可为空)
     * @param file 文件夹文件
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败.
     */
    public static String compressFolder(String destFileName, String passwd, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for(File f:files){
                if (f.isDirectory()){
                    compressFolder(destFileName, passwd, f);
                }else {
                    compress(destFileName,passwd,f);
                }
            }
        }
        return null;
    }

    //判断文件的文件扩展名是否有效
    public static boolean isValid(String contentType, String... allowTypes) {
        if (null == contentType || "".equals(contentType)) {
            return false;
        }
        for (String type : allowTypes) {
            if (contentType.contains(type)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {


        compressFolder("C:\\Users\\12\\Documents\\aa1.zip","123",new File("C:\\Users\\12\\Documents\\Tencent Files\\2310501264\\FileRecv"));

    }

}
