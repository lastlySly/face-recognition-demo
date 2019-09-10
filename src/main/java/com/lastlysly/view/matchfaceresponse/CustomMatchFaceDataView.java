package com.lastlysly.view.matchfaceresponse;

import java.io.File;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 15:38
 **/
public class CustomMatchFaceDataView {

    /**
     * 抓拍人脸文件
     */
    private File zpFile;

    /**
     * 车牌号
     */
    private String carPlaceNum;


    /**
     * 对比文件，得分
     */
    private List<CustomBzFileAndScoreView> customBzFileAndScoreViewList;


    public List<CustomBzFileAndScoreView> getCustomBzFileAndScoreViewList() {
        return customBzFileAndScoreViewList;
    }

    public void setCustomBzFileAndScoreViewList(List<CustomBzFileAndScoreView> customBzFileAndScoreViewList) {
        this.customBzFileAndScoreViewList = customBzFileAndScoreViewList;
    }

    public File getZpFile() {
        return zpFile;
    }

    public void setZpFile(File zpFile) {
        this.zpFile = zpFile;
    }

    public String getCarPlaceNum() {
        return carPlaceNum;
    }

    public void setCarPlaceNum(String carPlaceNum) {
        this.carPlaceNum = carPlaceNum;
    }

    @Override
    public String toString() {
        return "CustomMatchFaceDataView{" +
                "zpFile=" + zpFile +
                ", carPlaceNum='" + carPlaceNum + '\'' +
                ", customBzFileAndScoreViewList=" + customBzFileAndScoreViewList +
                '}';
    }
}
