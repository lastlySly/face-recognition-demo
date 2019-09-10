package com.lastlysly.view.facedetails;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 14:42
 **/
public class EyeStatusView {
    private Double right_eye;
    private Double left_eye;

    public Double getRight_eye() {
        return right_eye;
    }

    public void setRight_eye(Double right_eye) {
        this.right_eye = right_eye;
    }

    public Double getLeft_eye() {
        return left_eye;
    }

    public void setLeft_eye(Double left_eye) {
        this.left_eye = left_eye;
    }

    @Override
    public String toString() {
        return "EyeStatusView{" +
                "right_eye=" + right_eye +
                ", left_eye=" + left_eye +
                '}';
    }
}
