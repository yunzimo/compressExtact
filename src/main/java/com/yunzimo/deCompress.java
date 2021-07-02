package com.yunzimo;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class deCompress {


    /**
     * 根据所给密码解压zip压缩包到指定目录
     * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     *
     * @param zipFile zip压缩包绝对路径
     * @param dest 指定解压文件夹位置
     * @param passwd 密码(可为空)
     * @return 解压后的文件数组
     * @throws ZipException
     */
    @SuppressWarnings("unchecked")
    public static File[] deCompress(File zipFile, String dest, String passwd) throws ZipException {
        //1.判断指定目录是否存在
        File destDir = new File(dest);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        //2.初始化zip工具
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("UTF-8");
        if (!zFile.isValidZipFile()) {
            throw new ZipException("压缩文件不合法,可能被损坏.");
        }
        //3.判断是否已加密
        if (zFile.isEncrypted()) {
            zFile.setPassword(passwd.toCharArray());
        }
        //4.解压所有文件
        zFile.extractAll(dest);
        List<FileHeader> headerList = zFile.getFileHeaders();
        List<File> extractedFileList = new ArrayList<File>();
        for(FileHeader fileHeader : headerList) {
            if (!fileHeader.isDirectory()) {
                extractedFileList.add(new File(destDir,fileHeader.getFileName()));
            }
        }
        File [] extractedFiles = new File[extractedFileList.size()];
        extractedFileList.toArray(extractedFiles);
        return extractedFiles;
    }
    /**
     * 解压无密码的zip压缩包到指定目录
     * @param zipFile zip压缩包
     * @param dest 指定解压文件夹位置
     * @return 解压后的文件数组
     * @throws ZipException
     */
    public static File[] deCompress(File zipFile, String dest){
        try {
            return deCompress(zipFile, dest, null);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据所给密码解压zip压缩包到指定目录
     * @param zipFilePath zip压缩包绝对路径
     * @param dest 指定解压文件夹位置
     * @param passwd 压缩包密码
     * @return 解压后的所有文件数组
     */
    public static File[] deCompress(String zipFilePath, String dest, String passwd){
        try {
            return deCompress(new File(zipFilePath), dest, passwd);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 无密码解压压缩包到指定目录
     * @param zipFilePath zip压缩包绝对路径
     * @param dest 指定解压文件夹位置
     * @return 解压后的所有文件数组
     */
    public static File[] deCompress(String zipFilePath, String dest){
        try {
            return deCompress(new File(zipFilePath), dest, null);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return null;
    }
}
