package com.lastlysly.view.facedetails;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2019-09-09 11:46
 **/
public class AngleView {

    private Double roll;
    private Double pitch;
    private Double yaw;

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getYaw() {
        return yaw;
    }

    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    @Override
    public String toString() {
        return "AngleView{" +
                "roll=" + roll +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                '}';
    }
}
