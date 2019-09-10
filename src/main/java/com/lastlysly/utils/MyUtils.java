package com.lastlysly.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 10:07
 **/
public class MyUtils {

    /**
     * 扫描指定文件路径下所有人脸文件，并加入人脸库
     */
    public static void scanFilesAndAddToFaceDB(String filePath,String groupId) throws InterruptedException, IOException {
        List<String> filesPathList = Lists.newArrayList();
        MyFileUtils.getFilesPathList(filePath,filesPathList);
        ObjectMapper objectMapper = new ObjectMapper();
        for(int i = 0; i < filesPathList.size(); i++){
            File file = new File(filesPathList.get(i));
            UserInfo userInfo = new UserInfo();
            String carNumber = file.getName().replaceAll(".jpg","").split(" ")[0];
            String uid = UUID.randomUUID().toString().replaceAll("-","");

            userInfo.setId(uid);
            userInfo.setCarPlateNumber(carNumber);
            userInfo.setPhotoImgPath(filesPathList.get(i));
            String res = MyFaceRecognitionUtils.addUser(file, objectMapper.writeValueAsString(userInfo),uid,groupId);
            System.out.println(res);
            //            处理QPS 超限。当前为2。
            Thread.sleep(500);
        }
    }



    /**
     * 扫描指定文件路径下所有人脸文件，并做人脸检测，并存入excel
     * @param filePath
     */
    public static void scanFilesAndDetectFaceAndSaveExcel(String filePath,HSSFWorkbook hssfWorkbook) throws InterruptedException, IOException {
        List<String> filesPathList = Lists.newArrayList();
        MyFileUtils.getFilesPathList(filePath,filesPathList);
        List<DetectFaceInfoView> detectFaceInfoViewList = Lists.newArrayList();
        for(int i = 0; i < filesPathList.size(); i++){
            File file = new File(filesPathList.get(i));
            DetectFaceInfoView res =MyFaceRecognitionUtils.detectFace(file,"5");
            res.setFilePath(filesPathList.get(i));
            System.out.println("============scanFilesAndDetectFaceAndSaveExcel===============");
            System.out.println(res);
            detectFaceInfoViewList.add(res);
            //            处理QPS 超限。当前为2。
            Thread.sleep(500);
        }
        //输出Excel文件
//        File excel = new File("C:\\Users\\lastlySly\\Desktop\\test\\test.xls");

        detectFaceInfoToExcel(detectFaceInfoViewList,hssfWorkbook);


    }


    /**
     * 人脸检测数据导出到excel
     * @param detectFaceInfoViewList
     * @throws IOException
     */
    public static void detectFaceInfoToExcel(List<DetectFaceInfoView> detectFaceInfoViewList,HSSFWorkbook hssfWorkbook) throws IOException {

//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("人脸检测数据");
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
        cell.setCellValue("人脸检测数据明细");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
        //在sheet里创建第二行
        HSSFRow row2=hssfSheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("检测图片");
        row2.createCell(1).setCellValue("单张图片识别人脸数");
        row2.createCell(2).setCellValue("性别");
        row2.createCell(3).setCellValue("年龄");
        row2.createCell(4).setCellValue("双眼状态( [0,1]取值，越接近0闭合的可能性越大)");
        row2.createCell(5).setCellValue("人脸模糊程度(范围[0~1]，0表示清晰，1表示模糊)");
        row2.createCell(6).setCellValue("人脸完整度(0或1, 0为人脸溢出图像边界，1为人脸都在图像边界内)");
        row2.createCell(7).setCellValue("识别概率(人脸置信度，范围【0~1】，代表这是一张人脸的概率，0最小、1最大)");
        Iterator<Cell> iterator = row2.cellIterator();
        while (iterator.hasNext()){
            iterator.next().setCellStyle(cellStyle);
        }
        int row = 2;
        excelSetDetectFaceData(detectFaceInfoViewList, hssfWorkbook, hssfSheet, cellStyle, patriarch,row);

//        hssfWorkbook.write(outFile);
    }

    /**
     * 人脸检测excel数据填充
     * @param detectFaceInfoViewList
     * @param hssfWorkbook
     * @param hssfSheet
     * @param cellStyle
     * @param patriarch
     * @param row
     * @throws IOException
     */
    private static void excelSetDetectFaceData(List<DetectFaceInfoView> detectFaceInfoViewList,
                                               HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet,
                                               HSSFCellStyle cellStyle, HSSFPatriarch patriarch,
                                               int row) throws IOException {
        Iterator<Cell> iterator;
        for (int i = 0; i < detectFaceInfoViewList.size(); i++){

            String filePath = detectFaceInfoViewList.get(i).getFilePath();
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(new File(filePath));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            if (0 != detectFaceInfoViewList.get(i).getError_code()){
                //如果识别没有人脸

                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 0, row, (short) 1, row);
                anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                //插入图片
                patriarch.createPicture(anchor, hssfWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row,1,7));
                HSSFRow rowError=hssfSheet.createRow(row);
                HSSFCell errorCell = rowError.createCell(1);
                errorCell.setCellValue(detectFaceInfoViewList.get(i).getError_msg());
                errorCell.setCellStyle(cellStyle);
                row ++;
                continue;
            }

            int faceNum = detectFaceInfoViewList.get(i).getFace_list().size();


            if(faceNum > 1){
                //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row+faceNum-1,0,0));
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row+faceNum-1,1,1));
            }

            DetectFaceInfoView detectFaceInfoView = detectFaceInfoViewList.get(i);

            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 0, row, (short) 1, row+faceNum-1);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片
            patriarch.createPicture(anchor, hssfWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));


            for (int j = 0; j<faceNum;j++){
                //获取脸数据
                DetectFaceDetailsInfoView detectFaceDetailsInfoView = detectFaceInfoView.getFace_list().get(j);
                HSSFRow row3=hssfSheet.createRow(row);
                row3.createCell(1).setCellValue(detectFaceInfoView.getFace_num());

                row3.createCell(2).setCellValue(detectFaceDetailsInfoView.getGender().getType()
                        + "(性别置信度：" + detectFaceDetailsInfoView.getGender().getProbability() +")");

                row3.createCell(3).setCellValue(detectFaceDetailsInfoView.getAge());

                String eyeStatus = "";
                eyeStatus += "左眼：" + detectFaceDetailsInfoView.getEye_status().getLeft_eye() + "，";
                eyeStatus += "右眼：" + detectFaceDetailsInfoView.getEye_status().getRight_eye();
                row3.createCell(4).setCellValue(eyeStatus);
                row3.createCell(5).setCellValue(detectFaceDetailsInfoView.getQuality().getBlur());
                row3.createCell(6).setCellValue(detectFaceDetailsInfoView.getQuality().getCompleteness());
                row3.createCell(7).setCellValue(detectFaceDetailsInfoView.getFace_probability());
                iterator = row3.cellIterator();
                while (iterator.hasNext()){
                    iterator.next().setCellStyle(cellStyle);
                }
                row ++;
            }
            byteArrayOut.close();
//            row += faceNum;
        }
    }




    /**
     * 扫描指定文件夹，并进行人脸搜索，最后存入excel
     * @param filePath
     * @throws IOException
     * @throws InterruptedException
     */
    public static void scanFilesAndSearchFaceAndSaveExcel(String filePath,HSSFWorkbook hssfWorkbook) throws IOException, InterruptedException {
        List<String> filesPathList = Lists.newArrayList();
        MyFileUtils.getFilesPathList(filePath,filesPathList);
        List<SearchFaceResponseView> searchFaceResponseViewList = Lists.newArrayList();
        for (int i=0; i<filesPathList.size();i++){
            File file = new File(filesPathList.get(i));
            SearchFaceResponseView searchFaceResponseView =
                    MyFaceRecognitionUtils.searchFace(file,"test",null,"1");
            searchFaceResponseView.setFile(file);
            System.out.println("==============scanFilesAndSearchFaceAndSaveExcel===============");
            System.out.println(searchFaceResponseView.toString());
            searchFaceResponseViewList.add(searchFaceResponseView);
            //            处理QPS 超限。当前为2。
            Thread.sleep(500);

        }
//        File excel = new File("C:\\Users\\lastlySly\\Desktop\\test\\人脸搜索数据明细.xls");
        searchFaceInfoToExcel(searchFaceResponseViewList,hssfWorkbook);

    }


    /**
     * 人脸搜索数据导出到excel
     * @param searchFaceResponseViewList
     * @throws IOException
     */
    public static void searchFaceInfoToExcel(List<SearchFaceResponseView> searchFaceResponseViewList,
                                             HSSFWorkbook hssfWorkbook) throws IOException {

//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("人脸搜索数据明细");
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
        cell.setCellValue("人脸搜索数据明细");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        //在sheet里创建第二行
        HSSFRow row2=hssfSheet.createRow(1);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("检测图片");
        row2.createCell(1).setCellValue("匹配率");
        row2.createCell(2).setCellValue("用户id");
        row2.createCell(3).setCellValue("车牌号");
        row2.createCell(4).setCellValue("对应人脸");
        Iterator<Cell> iterator = row2.cellIterator();
        while (iterator.hasNext()){
            iterator.next().setCellStyle(cellStyle);
        }
        int row = 2;
        excelSetSearchFaceData(searchFaceResponseViewList, hssfWorkbook, hssfSheet, cellStyle, patriarch,row);

//        hssfWorkbook.write(outFile);
    }



    /**
     * 人脸搜索excel数据填充
     * @param searchFaceResponseViewList
     * @param hssfWorkbook
     * @param hssfSheet
     * @param cellStyle
     * @param patriarch
     * @param row
     * @throws IOException
     */
    private static void excelSetSearchFaceData(List<SearchFaceResponseView> searchFaceResponseViewList,
                                               HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet,
                                               HSSFCellStyle cellStyle, HSSFPatriarch patriarch,
                                               int row) throws IOException {
        Iterator<Cell> iterator;
        for (int i = 0; i < searchFaceResponseViewList.size(); i++){

            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            BufferedImage bufferImg = ImageIO.read(searchFaceResponseViewList.get(i).getFile());
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            if (0 != searchFaceResponseViewList.get(i).getError_code()){
                //如果识别没有人脸
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 0, row, (short) 1, row);
                anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                //插入图片
                patriarch.createPicture(anchor, hssfWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

                //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                hssfSheet.addMergedRegion(new CellRangeAddress(row,row,1,4));
                HSSFRow rowError=hssfSheet.createRow(row);
                HSSFCell errorCell = rowError.createCell(1);
                errorCell.setCellValue(searchFaceResponseViewList.get(i).getError_msg());
                errorCell.setCellStyle(cellStyle);
                row ++;
                continue;
            }



            //获取脸数据
            SearchFaceResultView searchFaceResultView = searchFaceResponseViewList.get(i).getResult();
            SearchFaceUserInfo searchFaceUserInfo = searchFaceResultView.getUser_list().get(0);
            //读取对应人脸的图片
            ByteArrayOutputStream returnFileByteArrayOut = new ByteArrayOutputStream();
            BufferedImage returnFileBufferImg = ImageIO.read(new File(searchFaceUserInfo.getUserInfo().getPhotoImgPath()));
            ImageIO.write(returnFileBufferImg, "jpg", returnFileByteArrayOut);
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 0, row, (short) 1, row);
            HSSFClientAnchor returnFileAnchor = new HSSFClientAnchor(0, 0, 0, 255,(short) 4, row, (short) 5, row);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            returnFileAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            //插入图片
            patriarch.createPicture(anchor, hssfWorkbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            patriarch.createPicture(returnFileAnchor, hssfWorkbook.addPicture(returnFileByteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));


            HSSFRow row3=hssfSheet.createRow(row);
            row3.createCell(1).setCellValue(searchFaceUserInfo.getScore());

            row3.createCell(2).setCellValue(searchFaceUserInfo.getUser_id());

            row3.createCell(3).setCellValue(searchFaceUserInfo.getUserInfo().getCarPlateNumber());
            iterator = row3.cellIterator();
            while (iterator.hasNext()){
                iterator.next().setCellStyle(cellStyle);
            }
            row ++;
            returnFileByteArrayOut.close();
            byteArrayOut.close();
        }
    }








    /**
     * 扫描指定两个路径文件夹并对比人脸，最后存入excel
     * @throws IOException
     */
    public static void scanFilesAndMatchFaceAndSaveToExcel(String filePath1,String filePath2,
                                                           HSSFWorkbook hssfWorkbook) throws IOException, InterruptedException {

        List<CustomMatchFaceDataView> customMatchFaceDataViewList = Lists.newArrayList();

//        String filePath1 = "F:/MyAllWorkProject/xmgps/百度人脸识别评估/抓拍的车载照片/抓拍/";
//        String filePath2 = "F:/MyAllWorkProject/xmgps/百度人脸识别评估/司机标准照片/新建文件夹 (2)/";
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
                    System.out.println("=======scanFilesAndMatchFaceAndSaveToExcel========");
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

            //            处理QPS 超限。当前为2。
            Thread.sleep(500);

        }



        matchFaceInfoToExcel(customMatchFaceDataViewList,hssfWorkbook);

    }






    /**
     * 人脸对比数据导出到excel
     * @param customMatchFaceDataViewList
     * @throws IOException
     */
    public static void matchFaceInfoToExcel(List<CustomMatchFaceDataView> customMatchFaceDataViewList,HSSFWorkbook hssfWorkbook) throws IOException {

//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
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
//        hssfWorkbook.write(outFile);
    }

    /**
     * 人脸对比excel数据填充
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

                if (customBzFileAndScoreView.getError_code() == 0){
                    String score = customBzFileAndScoreView.getScore() == null ? "" : customBzFileAndScoreView.getScore().toString();
                    row3.createCell(2).setCellValue(score);
                }else{
                    row3.createCell(2).setCellValue(customBzFileAndScoreView.getError_msg());
                }


                iterator = row3.cellIterator();
                while (iterator.hasNext()){
                    iterator.next().setCellStyle(cellStyle);
                }
                row ++;

            }

        }
    }





}
