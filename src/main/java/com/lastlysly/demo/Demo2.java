package com.lastlysly.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.lastlysly.utils.MyFaceRecognitionUtils;
import com.lastlysly.utils.MyFileUtils;
import com.lastlysly.utils.MyUtils;
import com.lastlysly.view.UserInfo;
import com.lastlysly.view.facedetails.DetectFaceDetailsInfoView;
import com.lastlysly.view.facedetails.DetectFaceInfoView;
import com.lastlysly.view.searchfaceresponse.SearchFaceResponseView;
import com.lastlysly.view.searchfaceresponse.SearchFaceResultView;
import com.lastlysly.view.searchfaceresponse.SearchFaceUserInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-08 11:24
 **/
public class Demo2 {
    public static void main(String[] args) throws InterruptedException, IOException {
//        String filePath = "F:/MyAllWorkProject/xmgps/百度人脸识别评估/司机标准照片/新建文件夹 (2)";
        String filePath = "F:\\MyAllWorkProject\\xmgps\\百度人脸识别评估\\抓拍的车载照片\\抓拍";
//        String filePath = "F:\MyAllWorkProject\xmgps\百度人脸识别评估\test2";
        String groupId = "test";
//        MyUtils.scanFilesAndAddToFaceDB(filePath,groupId);

//        test2();
//        test3();

        MyUtils.scanFilesAndSearchFaceAndSaveExcel(filePath);

//
//        MyUtils.scanFilesAndDetectFace(filePath);
    }


    /**
     * 人脸检测
     */
    public static void test2() throws IOException {

        String filePath = "F:\\MyAllWorkProject\\xmgps\\test\\1.jpg";
        File file = new File(filePath);
        List<DetectFaceInfoView> detectFaceInfoViewList = Lists.newArrayList();
        DetectFaceInfoView res =MyFaceRecognitionUtils.detectFace(file,"5");
        res.setFilePath(filePath);
        detectFaceInfoViewList.add(res);

        String filePath2 = "F:\\MyAllWorkProject\\xmgps\\test\\2.jpg";
        File file2 = new File(filePath2);
        DetectFaceInfoView res2 =MyFaceRecognitionUtils.detectFace(file2,"5");
        res2.setFilePath(filePath2);
        detectFaceInfoViewList.add(res2);
        try {
            //输出Excel文件
            File excel = new File("C:\\Users\\lastlySly\\Desktop\\test\\test.xls");
            MyUtils.detectFaceInfoToExcel(detectFaceInfoViewList,excel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 人脸搜索
     */
    public static void test3() throws IOException {
        File file = new File("F:\\MyAllWorkProject\\xmgps\\百度人脸识别评估\\test2\\闽DTH132潘新举\\闽DTH132_20180112091417_0_1_4905300.jpg");
        SearchFaceResponseView res = MyFaceRecognitionUtils.searchFace(file,"test",null,"1");
    }


}
