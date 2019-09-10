package com.lastlysly.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lastlysly.utils.MyFaceRecognitionUtils;
import com.lastlysly.utils.MyFileUtils;
import com.lastlysly.utils.MyUtils;
import com.lastlysly.view.UserInfo;
import com.lastlysly.view.facedetails.DetectFaceDetailsInfoView;
import com.lastlysly.view.facedetails.DetectFaceInfoView;
import com.lastlysly.view.matchfaceresponse.CustomBzFileAndScoreView;
import com.lastlysly.view.matchfaceresponse.CustomMatchFaceDataView;
import com.lastlysly.view.matchfaceresponse.MatchFaceResponseView;
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
        String groupId = "test";
//        MyUtils.scanFilesAndAddToFaceDB(filePath,groupId);

//        test2();
//        test3();

//        MyUtils.scanFilesAndSearchFaceAndSaveExcel(filePath);
//        MyUtils.scanFilesAndDetectFaceAndSaveExcel(filePath);


//        test1();
        scanFilesAndMatchFaceAndSaveToExcel();

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

    /**
     * 人脸对比
     */
    public static void test1() throws IOException {
        File file1 = new File("F:\\MyAllWorkProject\\xmgps\\百度人脸识别评估\\抓拍的车载照片\\抓拍\\闽DTH132潘新举\\闽DTH132_20180112091417_0_1_4905300.jpg");
        File file2 = new File("F:\\MyAllWorkProject\\xmgps\\百度人脸识别评估\\司机标准照片\\新建文件夹 (2)\\闽DTH132 1.jpg");
//        File file2 = new File("C:\\Users\\lastlySly\\Desktop\\test\\demo.jpeg");
        MatchFaceResponseView res = MyFaceRecognitionUtils.matchFace(file1,file2);

        System.out.println(res);

    }


    public static void scanFilesAndMatchFaceAndSaveToExcel() throws IOException {

        List<CustomMatchFaceDataView> customMatchFaceDataViewList = Lists.newArrayList();

        String filePath1 = "F:/MyAllWorkProject/xmgps/百度人脸识别评估/抓拍的车载照片/抓拍/";
        String filePath2 = "F:/MyAllWorkProject/xmgps/百度人脸识别评估/司机标准照片/新建文件夹 (2)/";
        /**
         * 文件路径集合1
         */
        List<String> filesPathList1 = Lists.newArrayList();
        MyFileUtils.getFilesPathList(filePath1,filesPathList1);
        List<Map<String,Object>> zpFileMapList = Lists.newArrayList();
        /**
         * 文件路径集合2
         */
        Map<String,List<File>> bzFileMap = Maps.newHashMap();
        MyFileUtils.getFilesMap(filePath2,bzFileMap);

        filesPathList1.forEach(fp ->{
            File file = new File(fp);
            Map<String,Object> map = Maps.newHashMap();
            String carPlaceNum = file.getName().substring(0,7);
            map.put("carPlaceNum",carPlaceNum);
            map.put("file",file);
            zpFileMapList.add(map);
        });


        //
        for (int i = 0; i<zpFileMapList.size(); i++){
            CustomMatchFaceDataView customMatchFaceDataView = new CustomMatchFaceDataView();
            File zpFile = (File) zpFileMapList.get(i).get("file");
            String zpFileName = (String) zpFileMapList.get(i).get("carPlaceNum");
            customMatchFaceDataView.setCarPlaceNum(zpFileName);
            customMatchFaceDataView.setZpFile(zpFile);

            List<CustomBzFileAndScoreView> customBzFileAndScoreViewList = Lists.newArrayList();
            if(bzFileMap.containsKey(zpFileName)){
                List<File> bzFileList = bzFileMap.get(zpFileName);
                for (File file : bzFileList){
                    CustomBzFileAndScoreView customBzFileAndScoreView = new CustomBzFileAndScoreView();
                    customBzFileAndScoreView.setBzFile(file);

                    MatchFaceResponseView matchFaceResponseView = MyFaceRecognitionUtils.matchFace(zpFile,file);
                    System.out.println("=======res========");
                    System.out.println(matchFaceResponseView);
                    customBzFileAndScoreView.setError_code(matchFaceResponseView.getError_code());
                    customBzFileAndScoreView.setError_msg(matchFaceResponseView.getError_msg());
                    if (matchFaceResponseView.getError_code() == 0){
                        customBzFileAndScoreView.setScore(matchFaceResponseView.getResult().getScore());
                    }

                    customBzFileAndScoreViewList.add(customBzFileAndScoreView);
                }
            }
            customMatchFaceDataView.setCustomBzFileAndScoreViewList(customBzFileAndScoreViewList);
            customMatchFaceDataViewList.add(customMatchFaceDataView);

        }




        //生成excel
        //输出Excel文件
        File excel = new File("C:\\Users\\lastlySly\\Desktop\\test\\人脸比对信息.xls");

        matchFaceInfoToExcel(customMatchFaceDataViewList,excel);

    }






    /**
     * 人脸对比数据导出到excel
     * @param customMatchFaceDataViewList
     * @param outFile
     * @throws IOException
     */
    public static void matchFaceInfoToExcel(List<CustomMatchFaceDataView> customMatchFaceDataViewList,File outFile) throws IOException {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("人脸对比数据");
        hssfSheet.setDefaultRowHeightInPoints(100);
        hssfSheet.setDefaultColumnWidth(30);
        HSSFCellStyle cellStyle=hssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = hssfSheet.createDrawingPatriarch();

        HSSFRow hssfRow1 = hssfSheet.createRow(0);
        HSSFCell cell = hssfRow1.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("人脸对比数据明细");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
        //在sheet里创建第二行
        HSSFRow row2=hssfSheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("抓拍图片");
        row2.createCell(1).setCellValue("车牌号");
        row2.createCell(2).setCellValue("人脸相似度");
        row2.createCell(3).setCellValue("标准人脸图片");
        Iterator<Cell> iterator = row2.cellIterator();
        while (iterator.hasNext()){
            iterator.next().setCellStyle(cellStyle);
        }
        int row = 2;

        excelSetMatchFaceData(customMatchFaceDataViewList, hssfWorkbook, hssfSheet, cellStyle, patriarch,row);
        hssfWorkbook.write(outFile);
    }

    /**
     * 人脸检测excel数据填充
     * @param customMatchFaceDataViewList
     * @param hssfWorkbook
     * @param hssfSheet
     * @param cellStyle
     * @param patriarch
     * @param row
     * @throws IOException
     */
    private static void excelSetMatchFaceData(List<CustomMatchFaceDataView> customMatchFaceDataViewList,
                                               HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet,
                                               HSSFCellStyle cellStyle, HSSFPatriarch patriarch,
                                               int row) throws IOException {
        Iterator<Cell> iterator;
        for (int i = 0; i < customMatchFaceDataViewList.size(); i++){
            CustomMatchFaceDataView customMatchFaceDataView = customMatchFaceDataViewList.get(i);
            File zpFile = customMatchFaceDataView.getZpFile();
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(zpFile);
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            int matchFileSize = customMatchFaceDataView.getCustomBzFileAndScoreViewList().size();

//            找不到匹配车牌号的标准人脸照片
            if (matchFileSize == 0){
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row,2,3));
                HSSFRow rowError=hssfSheet.createRow(row);
                HSSFCell errorCell1 = rowError.createCell(1);
                errorCell1.setCellValue(customMatchFaceDataView.getCarPlaceNum());
                errorCell1.setCellStyle(cellStyle);

                HSSFCell errorCell2 = rowError.createCell(2);
                errorCell2.setCellValue("找不到匹配车牌号的标准人脸照片");
                errorCell2.setCellStyle(cellStyle);


                //抓拍图片插入，，file1
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 0, row, (short) 1, row);
                anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                //插入图片
                patriarch.createPicture(anchor, hssfWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                byteArrayOut.close();

                row ++;
                continue;
            }

            if (matchFileSize > 1){
                //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row+matchFileSize-1,0,0));
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row+matchFileSize-1,1,1));
            }

            //抓拍图片插入，，file1
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 0, row, (short) 1, row+matchFileSize-1);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片
            patriarch.createPicture(anchor, hssfWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

            byteArrayOut.close();

            /**
             * 遍历比对完的数据
             */
            for (int j = 0; j<matchFileSize; j++){

                /**
                 * 获取比对完的信息
                 */
                CustomBzFileAndScoreView customBzFileAndScoreView = customMatchFaceDataView
                        .getCustomBzFileAndScoreViewList().get(j);

                ByteArrayOutputStream bzByteArrayOut = new ByteArrayOutputStream();
                BufferedImage bzBufferImg = ImageIO.read(customBzFileAndScoreView.getBzFile());
                ImageIO.write(bzBufferImg, "jpg", bzByteArrayOut);

                //标准图片插入，，file2
                HSSFClientAnchor bzAnchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 3, row, (short) 4, row);
                bzAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                //插入图片
                patriarch.createPicture(bzAnchor, hssfWorkbook.addPicture(bzByteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                HSSFRow row3=hssfSheet.createRow(row);
                row3.createCell(1).setCellValue(customMatchFaceDataView.getCarPlaceNum());
                String score = customBzFileAndScoreView.getScore() == null ? "" : customBzFileAndScoreView.getScore().toString();
                row3.createCell(2).setCellValue(score);
                iterator = row3.cellIterator();
                while (iterator.hasNext()){
                    iterator.next().setCellStyle(cellStyle);
                }
                row ++;

            }

        }
    }




}
