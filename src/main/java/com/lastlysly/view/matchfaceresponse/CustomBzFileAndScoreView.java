package com.lastlysly.view.matchfaceresponse;

import java.io.File;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-10 15:47
 * 对应标准人脸，以及计算得分
 **/
public class CustomBzFileAndScoreView {
    /**
     * 标准人脸文件
     */
    private File bzFile;

    /**
     * 人脸相似度得分
     */
    private Double score;

    private String error_msg;

    private int error_code;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public File getBzFile() {
        return bzFile;
    }

    public void setBzFile(File bzFile) {
        this.bzFile = bzFile;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "CustomBzFileAndScoreView{" +
                "bzFile=" + bzFile +
                ", score=" + score +
                '}';
    }
}
