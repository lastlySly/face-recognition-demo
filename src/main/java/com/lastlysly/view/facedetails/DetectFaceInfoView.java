package com.lastlysly.view.facedetails;

import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 11:28
 **/
public class DetectFaceInfoView {
    private String face_num;
    private List<DetectFaceDetailsInfoView> face_list;

    private String error_msg;
    private int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getFace_num() {
        return face_num;
    }

    public void setFace_num(String face_num) {
        this.face_num = face_num;
    }

    public List<DetectFaceDetailsInfoView> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<DetectFaceDetailsInfoView> face_list) {
        this.face_list = face_list;
    }

    @Override
    public String toString() {
        return "DetectFaceInfoView{" +
                "face_num='" + face_num + '\'' +
                ", face_list=" + face_list +
                '}';
    }

    /*==============以下字段不是来源于api返回，仅自定义工具类使用===================*/
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
