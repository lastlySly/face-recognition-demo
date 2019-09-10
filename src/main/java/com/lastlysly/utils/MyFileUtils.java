package com.lastlysly.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.List;
import java.util.Map;

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



    /**
     * 获取某路径下所有文件的Map，文件名 ->
     * @param path
     * @return
     */
    public static void getFilesMap(String path,Map<String,List<File>> map) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
//                    System.out.println("目录：" + files[i].getPath());
                    getFilesMap(files[i].getPath(),map);
                } else {

                    //对文件名裁剪，车牌号为文件名前7位
                    String carPlaceNum = files[i].getName().substring(0,7);
                    System.out.println(carPlaceNum);
                    //如果map中已有该key，则追加
                    if (map.containsKey(carPlaceNum)){
                        map.get(carPlaceNum).add(files[i]);
                    }else{
                        //否则，新增
                        List<File> fileList = Lists.newArrayList();
                        fileList.add(files[i]);
                        map.put(carPlaceNum,fileList);
                    }
//                    System.out.println("文件：" + files[i].getPath());
                }

            }
        } else {
            //对文件名裁剪，车牌号为文件名前7位
            String carPlaceNum = file.getName().substring(0,7);
            System.out.println(carPlaceNum);
            //如果map中已有该key，则追加
            if (map.containsKey(carPlaceNum)){
                map.get(carPlaceNum).add(file);
            }else{
                //否则，新增
                List<File> fileList = Lists.newArrayList();
                fileList.add(file);
                map.put(carPlaceNum,fileList);
            }
        }
    }

}
