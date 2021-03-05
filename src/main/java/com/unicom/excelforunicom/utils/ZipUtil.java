package com.unicom.excelforunicom.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Hukaihan
 */
public class ZipUtil {
    /**
     * 打包成zip包并输出至客户端
     */
    public static void generateZip(HttpServletResponse response, List<File> files) throws Exception {
        ZipOutputStream out = null;
        ServletOutputStream outputStream = response.getOutputStream();
        OutputStream toClient = new BufferedOutputStream(outputStream);
        try {
            byte[] buffer = new byte[1024];
            out = new ZipOutputStream(toClient);
                    for (File file : files) {
                        FileInputStream fis = new FileInputStream(file);
                        out.putNextEntry(new ZipEntry(file.getName()));
                        int len;
                        //读入需要下载的文件的内容，打包到zip文件
                        while ((len = fis.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                out.flush();
                out.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    /**
     * 根据特定前缀文件名打包
     * frontName 前缀名
     * sign 分割名字的符号
     */
    public static void generateZip(HttpServletResponse response, List<File> files, String frontName, String sign) throws Exception {
        ZipOutputStream out = null;
        ServletOutputStream outputStream = response.getOutputStream();
        OutputStream toClient = new BufferedOutputStream(outputStream);
        String fileFrontName = null;
        try {
            byte[] buffer = new byte[1024];
            out = new ZipOutputStream(toClient);
            for (File file : files) {
                fileFrontName = file.getName().substring(0,file.getName().indexOf(sign));
                if(frontName.equals(fileFrontName)){
                    FileInputStream fis = new FileInputStream(file);
                    out.putNextEntry(new ZipEntry(file.getName()));
                    int len;
                    //读入需要下载的文件的内容，打包到zip文件
                    while ((len = fis.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    out.flush();
                    out.closeEntry();
                    fis.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 递归某一文件夹下的文件
     */
    public static List<File> getFiles(String path) throws Exception {
        //目标集合fileList
        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if(file.isDirectory()){
            File []files = file.listFiles();
            for(File fileIndex:files){
                //如果这个文件是目录，则进行递归搜索
                if(fileIndex.isDirectory()){
                    getFiles(fileIndex.getPath());
                }else {
                    //如果文件是普通文件，则将文件句柄放入集合中
                    fileList.add(fileIndex);
                }
            }
        }
        return fileList;
    }

    /**
     * 根据前缀名查找文件
     * @param path 目录名称
     * @param frontName 前缀名
     * @param sign 分隔符
     * @return
     * @throws Exception
     */
    public static List<File> getFiles(String path,String frontName,String sign) throws Exception {
        //目标集合fileList
        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(path);
        String fileFrontName = null;
        if(file.isDirectory()){
            File []files = file.listFiles();
            for(File fileIndex : files){
                //如果这个文件是目录，则进行递归搜索
                if(fileIndex.isDirectory()){
                    getFiles(fileIndex.getPath());
                }else {
                    //如果文件是普通文件，则将文件句柄放入集合中
                    fileFrontName = fileIndex.getName().substring(0,fileIndex.getName().indexOf(sign));
                    if(fileFrontName.equals(frontName)){
                        fileList.add(fileIndex);
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * 删除某一目录下的所有文件
     */
    public static void deleteFiles(String path) throws Exception {
        File file = new File(path);
        if(file.isDirectory()){
            File []files = file.listFiles();
            for(File fileIndex:files){
                fileIndex.delete();
            }
        }
    }
}
