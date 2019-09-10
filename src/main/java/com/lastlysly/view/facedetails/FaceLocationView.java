package com.lastlysly.view.facedetails;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 11:42
 **/
public class FaceLocationView {
    private Double top;
    private Double left;
    private Double rotation;
    private Double width;
    private Double height;

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getRotation() {
        return rotation;
    }

    public void setRotation(Double rotation) {
        this.rotation = rotation;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "FaceLocationView{" +
                "top=" + top +
                ", left=" + left +
                ", rotation=" + rotation +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
