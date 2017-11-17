package com.jg.jsonform.utils;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * 文件工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:19
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class IFileUtils {

    /*文件根目录*/
    public static final String FILE_ROOT_DIRECTORY = "Wayto";
    /*项目根目录*/
    public static final String PROJECT_ROOT_DIRECTORY = "Base";
    /*图片文件夹*/
    public static final String IMAGE_DEIRECTORY = "image";
    /*缓存文件夹*/
    public static final String IMAGE_CATCH_DIR = "catch";
    /*下载目录*/
    public static final String DOWNLOAD_DIR = "download";
    /*离线地图文件*/
    public static final String OFFLINE_MAP = "map";


    /**
     * author: hezhiWu
     * created at 2017/7/7 14:05
     * <p>
     * 读取sd卡上指定后缀的所有文件
     *
     * @param filePath 路径(可传入sd卡路径)
     * @param suffere  后缀名称 比如 .gif
     */
    public static List<File> getSuffixFile(String filePath, String suffere) {
        List<File> files = new ArrayList<>();

        File f = new File(filePath);

        if (!f.exists()) {
            return null;
        }

        File[] subFiles = f.listFiles();
        for (File subFile : subFiles) {
            if (subFile.isFile() && subFile.getName().endsWith(suffere)) {
                files.add(subFile);
            } else if (subFile.isDirectory()) {
                getSuffixFile(subFile.getAbsolutePath(), suffere);
            } else {
                //非指定目录文件 不做处理
            }
        }
        return files;
    }


    /**
     * SD卡剩余容量
     * <p>
     * author: hezhiWu
     * created at 2017/3/22 17:27
     */
    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }

    /**
     * 获取SDK总容量
     * <p>
     * author: hezhiWu
     * created at 2017/3/22 17:28
     */
    public static long getSDAllSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; //单位MB
    }


    /**
     * 获取SD卡根目录
     *
     * @return
     */
    public static String getSDROOT() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 图片缓存地址
     *
     * @return
     */
    public static String getImageCatchDir() {
        String path = getSDROOT() + File.separator + FILE_ROOT_DIRECTORY  + File.separator + IMAGE_CATCH_DIR;
        File file = new File(path);
        if (!file.exists()) {
            File filePath = file.getParentFile();
            filePath.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 下载目录
     *
     * @return
     */
    public static String getDownloadDir() {
        return getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + DOWNLOAD_DIR;
    }

    /**
     * 离线地图目录
     * <p>
     * author: hezhiWu
     * created at 2017/7/7 11:24
     */
    public static String getOfflineMap() {
        return getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + OFFLINE_MAP;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return file.delete();
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 获取指定文件大小
     * <p>
     * author: hezhiWu
     * created at 2017/5/12 22:08
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取文件夹大小
     * <p>
     * author: hezhiWu
     * created at 2017/5/16 19:47
     */
    public static long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    /**
     * 获取缓存文件大小
     * <p>
     * author: hezhiWu
     * created at 2017/5/16 20:03
     */
    public static String getCatchFileSize() {
        return getReadableFileSize(getTotalSizeOfFilesInDir(new File(getImageCatchDir())));
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = files[i].delete();
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0KB";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 解压缩功能.将zipFile文件解压到folderPath目录下.
     * <p>
     * author: hezhiWu
     * created at 2017/7/17 10:08
     */
    /**
     * 62     * 解压缩一个文件
     * 63     *
     * 64     * @param zipFile 要解压的压缩文件
     * 65     * @param folderPath 解压缩的目标目录
     * 66     * @throws IOException 当解压缩过程出错时抛出
     * 67
     */
    public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }

        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[1024];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
    }
}
