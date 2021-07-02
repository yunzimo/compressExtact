package com.yunzimo;



import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class readZipFileName {

    //读取zip文件内的文件,返回文件名称列表
    public static List<String> readFileName(String path){
        List<String> list = new ArrayList<>();
        try {
            ZipFile zipFile = new ZipFile(path, Charset.forName("gbk"));
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                list.add(entries.nextElement().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> list = readFileName("C:\\Users\\12\\Documents\\aa1.zip");
        for(String str:list){
            System.out.println(str);
        }

    }
}
