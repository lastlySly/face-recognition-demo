package com.lastlysly.utils;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 08:40
 **/
public class MyFileUtils {

    /**
     * 获取某路径下所有文件路径
     * @param path
     * @return
     */
    public static void getFilesPathList(String path,List<String> filePathList) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
//                    System.out.println("目录：" + files[i].getPath());
                    getFilesPathList(files[i].getPath(),filePathList);
                } else {
                    filePathList.add(files[i].getPath());
//                    System.out.println("文件：" + files[i].getPath());
                }

            }
        } else {
            filePathList.add(file.getPath());
//            System.out.println("文件：" + file.getPath());
        }
    }
}
